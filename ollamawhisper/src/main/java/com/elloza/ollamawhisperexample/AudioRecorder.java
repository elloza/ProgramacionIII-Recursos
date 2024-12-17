package com.elloza.ollamawhisperexample;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AudioRecorder implements AutoCloseable {
    private static final AudioFormat FORMAT = new AudioFormat(
        16000,                     // Sample rate
        16,                        // Sample size in bits
        1,                         // Channels
        true,                      // Signed
        false                      // Little Endian (!)
    );
    private final AtomicBoolean isRecording = new AtomicBoolean(false);
    private volatile boolean isAnimating = false;
    private TargetDataLine line;
    private Thread captureThread;
    private Thread animationThread;
    private ByteArrayOutputStream audioData;

    public AudioRecorder() throws Exception {
        // Configurar JNativeHook
        Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                if (e.getKeyCode() == NativeKeyEvent.VC_SPACE) {
                    try {
                        stopRecording();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void startRecordingAnimation() {
        isAnimating = true;
        animationThread = new Thread(() -> {
            String[] frames = {" .", " ..", " ...", "    "};
            int frameIndex = 0;
            
            while (isAnimating) {
                System.out.print("\rGrabando" + frames[frameIndex]);
                frameIndex = (frameIndex + 1) % frames.length;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.print("\r"); // Limpiar la línea al terminar
        });
        animationThread.start();
    }

    private void stopRecordingAnimation() {
        isAnimating = false;
        if (animationThread != null) {
            try {
                animationThread.join(1000); // Esperar máximo 1 segundo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void startRecording() throws LineUnavailableException {
        // Configurar la grabación
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, FORMAT);
        if (!AudioSystem.isLineSupported(info)) {
            throw new IllegalStateException("Formato de audio no soportado");
        }

        line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(FORMAT);
        line.start();
        isRecording.set(true);
        audioData = new ByteArrayOutputStream();

        captureThread = new Thread(() -> {
            byte[] buffer = new byte[4096];
            while (isRecording.get()) {
                int count = line.read(buffer, 0, buffer.length);
                if (count > 0) {
                    audioData.write(buffer, 0, count);
                }
            }
        });
        captureThread.start();
        startRecordingAnimation();
    }

    public void stopRecording() throws InterruptedException {
        isRecording.set(false);
        stopRecordingAnimation();
        if (line != null) {
            line.stop();
            line.close();
        }
        if (captureThread != null) {
            captureThread.join();
        }
    }

    public float[] getAudioSamples() throws IOException {
        byte[] audioBytes = audioData.toByteArray();
        
        // Crear un ByteBuffer con orden Little Endian
        ByteBuffer captureBuffer = ByteBuffer.allocate(audioBytes.length);
        captureBuffer.order(ByteOrder.LITTLE_ENDIAN);
        captureBuffer.put(audioBytes);
        captureBuffer.rewind();
        
        // Obtener las muestras de 16 bits
        var shortBuffer = captureBuffer.asShortBuffer();
        float[] samples = new float[audioBytes.length / 2];
        
        int i = 0;
        while (shortBuffer.hasRemaining()) {
            samples[i++] = Float.max(-1f, Float.min(((float) shortBuffer.get()) / (float) Short.MAX_VALUE, 1f));
        }
        
        return samples;
    }

    public boolean isRecording() {
        return isRecording.get();
    }

    public void saveToWavFile(String filePath) throws IOException {
        byte[] audioBytes = audioData.toByteArray();
        AudioInputStream audioInputStream = new AudioInputStream(
                new ByteArrayInputStream(audioBytes),
                FORMAT,
                audioBytes.length / FORMAT.getFrameSize()
        );
        
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(filePath));
    }

    @Override
    public void close() throws Exception {
        stopRecordingAnimation();
        GlobalScreen.unregisterNativeHook();
    }
} 