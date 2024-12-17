package com.elloza.ollamawhisperexample;

import io.github.givimad.whisperjni.WhisperContext;
import io.github.givimad.whisperjni.WhisperFullParams;
import io.github.givimad.whisperjni.WhisperJNI;
import io.github.givimad.whisperjni.WhisperJNI.LoadOptions;
import io.github.givimad.whisperjni.WhisperSamplingStrategy;
import io.github.givimad.whisperjni.WhisperState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WhisperTranscriber implements AutoCloseable {
    private static WhisperJNI whisper;
    private static WhisperContext context;
    private static Path currentModelPath;
    private final int numThreads;
    private final boolean enableLogging;
    
    static {
        try {
            WhisperJNI.loadLibrary();
            whisper = new WhisperJNI();
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar Whisper JNI", e);
        }
    }

    public WhisperTranscriber(Path modelPath) throws IOException {
        this(modelPath, Runtime.getRuntime().availableProcessors(), false);
    }

    public WhisperTranscriber(Path modelPath, int numThreads, boolean enableLogging) throws IOException {
        this.numThreads = numThreads;
        this.enableLogging = enableLogging;
        
        // Configurar logging
        WhisperJNI.setLibraryLogger(enableLogging ? System.out::println : null);
        
        if (context == null || !modelPath.equals(currentModelPath)) {
            // Liberar el contexto anterior si existe
            if (context != null) {
                context.close();
            }
            
            if (!Files.exists(modelPath)) {
                throw new IOException("No se encontró el modelo en: " + modelPath);
            }
            
            // Inicializar el nuevo contexto
            context = whisper.initNoState(modelPath);
            if (context == null) {
                throw new IOException("No se pudo inicializar el modelo");
            }
            currentModelPath = modelPath;
        }
    }

    public String transcribe(float[] samples) throws Exception {
        try (WhisperState state = whisper.initState(context)) {
            WhisperFullParams params = new WhisperFullParams(WhisperSamplingStrategy.GREEDY);
            params.language = "es";
            params.printProgress = enableLogging;
            params.printTimestamps = enableLogging;
            params.nThreads = numThreads;
            
            int result = whisper.fullWithState(context, state, params, samples, samples.length);
            if (result != 0) {
                throw new IOException("Error en la transcripción, código: " + result);
            }
            
            StringBuilder transcription = new StringBuilder();
            int segmentCount = whisper.fullNSegmentsFromState(state);
            for (int j = 0; j < segmentCount; j++) {
                transcription.append(whisper.fullGetSegmentTextFromState(state, j));
            }
            
            return transcription.toString();
        }
    }

    @Override
    public void close() {
        // El contexto se mantendrá vivo para futuras instancias
        // Solo se liberará si se carga un modelo diferente
    }

    // Método para liberar recursos explícitamente
    public static void shutdown() {
        if (context != null) {
            context.close();
            context = null;
            currentModelPath = null;
        }
    }
} 