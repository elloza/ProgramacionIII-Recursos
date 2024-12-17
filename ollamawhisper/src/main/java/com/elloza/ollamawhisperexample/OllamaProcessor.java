package com.elloza.ollamawhisperexample;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.utils.OptionsBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class OllamaProcessor implements AutoCloseable {
    private static final String SYSTEM_PROMPT = """
            Eres un asistente experto en extraer información estructurada.
            Debes extraer la información en formato JSON con los siguientes campos:
            {
                "titulo": "título corto de la tarea",
                "date": "fecha mencionada como dd-mm-yyyy",
                "time": "hora si la hubiera, en caso contrario vacíO"
                "contenido": "descripción detallada de la tarea en formato MarkDown"
            }
            Responde ÚNICAMENTE con el JSON, sin explicaciones adicionales y sin comentarios.
            """;

    private final OllamaAPI ollamaAPI;
    private final String model;
    private final Gson gson;

    public OllamaProcessor(String host, String model) {
        this(host, model, false);
    }

    public OllamaProcessor(String host, String model, boolean enableLogging) {
        this.ollamaAPI = new OllamaAPI(host);
        this.model = model;
        this.gson = new Gson();
        
        // Configurar nivel de logging
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(enableLogging ? Level.INFO : Level.OFF);
    }

    public LLMResponse processText(String text) throws Exception {
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(model);
        
        var options = new OptionsBuilder()
            .setTemperature(0.7f)
            .setTopK(50)
            .setTopP(0.9f)
            .setNumPredict(500)
            .build();

        OllamaChatRequest request = builder
            .withMessage(OllamaChatMessageRole.SYSTEM, SYSTEM_PROMPT)
            .withMessage(OllamaChatMessageRole.USER, text)
            .withOptions(options)
            .build();

        OllamaChatResult result = ollamaAPI.chat(request);
        String rawResponse = result.getResponse()
            .replaceAll("```json\\s*", "") // Eliminar ```json
            .replaceAll("```\\s*$", "")    // Eliminar ``` final
            .trim();                       // Eliminar espacios extra
            
        JsonObject jsonResponse = null;
        
        try {
            jsonResponse = gson.fromJson(rawResponse, JsonObject.class);
        } catch (JsonSyntaxException e) {
            System.err.println("Error al parsear JSON: " + e.getMessage());
        }
        
        return new LLMResponse(rawResponse, jsonResponse);
    }

    @Override
    public void close() {
        // No cleanup needed for now
    }
} 