package com.elloza.ollamawhisperexample;

import java.nio.file.Path;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class Main {

    private static Path getDownloadsPath() {
        String userHome = System.getProperty("user.home");
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win") || os.contains("mac")) {
            return Path.of(userHome, "Downloads");
        }
        return Path.of(userHome, "Downloads");
    }

    private static void processAndDisplayLLMResult(OllamaProcessor ollama, String transcription) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("\nProcesando con LLM...");
        long startTime = System.currentTimeMillis();

        try {
            LLMResponse llmResponse = ollama.processText(transcription);
            displayLLMResponse(llmResponse, gson, startTime);
        } catch (IllegalStateException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void displayLLMResponse(LLMResponse llmResponse, Gson gson, long startTime) {
        System.out.println("\nRespuesta original del modelo:");
        System.out.println(llmResponse.getRawResponse());

        JsonObject taskJson = llmResponse.getJsonResponse();
        if (taskJson == null) {
            System.err.println("No se pudo procesar la respuesta como JSON válido");
            return;
        }

        System.out.println("\nJSON procesado:");
        System.out.println(gson.toJson(taskJson));

        validateAndDisplayTask(taskJson, gson, startTime);
    }

    private static void validateAndDisplayTask(JsonObject taskJson, Gson gson, long startTime) {
        Task task = Task.fromJson(taskJson);
        displayProcessingTime(startTime);
        System.out.println(task);
    }

    private static void displayProcessingTime(long startTime) {
        long endTime = System.currentTimeMillis();
        double seconds = (endTime - startTime) / 1000.0;
        System.out.println("\nProcesamiento completado en " + String.format("%.2f", seconds) + " segundos");
    }

    private static void processAudioRecording(AudioRecorder recorder, WhisperTranscriber transcriber,
            OllamaProcessor ollama, int contador) throws Exception {
        System.out.println("\nGrabación #" + contador);
        System.out.println("Presiona ESPACIO para detener la grabación...");

        // Grabar audio
        recorder.startRecording();
        while (recorder.isRecording()) {
            Thread.sleep(100);
        }
        recorder.stopRecording();
        System.out.println("\nGrabación finalizada.");

        // Guardar audio
        // String nombreArchivo = "grabacion_" + contador + ".wav";
        // recorder.saveToWavFile(nombreArchivo);

        // Transcribir
        System.out.println("Transcribiendo...");
        long startTime = System.currentTimeMillis();
        String transcription = transcriber.transcribe(recorder.getAudioSamples());
        displayProcessingTime(startTime);
        System.out.println("Texto: " + transcription);

        // Procesar con LLM y mostrar por pantalla
        processAndDisplayLLMResult(ollama, transcription);
    }

    public static void main(String[] args) {

        // try with resources
        try (
            AudioRecorder recorder = new AudioRecorder(); 
            WhisperTranscriber transcriber = new WhisperTranscriber(
                    getDownloadsPath().resolve("ggml-large-v3.bin"),
                    16,
                    false);
            OllamaProcessor ollama = new OllamaProcessor(
                    "http://localhost:11434",
                    "llama3.2",
                    false);
            Scanner scanner = new Scanner(System.in);
            )
            {

            int contador = 1;
            boolean continuar = true;

            while (continuar) {
                processAudioRecording(recorder, transcriber, ollama, contador);
                System.out.println("\n¿Deseas realizar otra grabación? (s/n): ");
                continuar = scanner.nextLine().trim().toLowerCase().equals("s");
                contador++;
            }

        } catch (Exception e) {
            System.err.println("Error general  en la aplicación:");
            e.printStackTrace();
        } finally {
            WhisperTranscriber.shutdown();
        }
    }
}