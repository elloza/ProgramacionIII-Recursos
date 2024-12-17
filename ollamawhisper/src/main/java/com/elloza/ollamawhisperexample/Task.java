package com.elloza.ollamawhisperexample;

import com.google.gson.JsonObject;

public class Task {
    private String titulo;
    private String date;
    private String contenido;
    private String time;

    public static Task fromJson(JsonObject json) throws IllegalStateException {
        validateRequiredFields(json);
        
        Task task = new Task();
        task.titulo = json.get("titulo").getAsString();
        task.date = json.get("date").getAsString();
        task.contenido = json.get("contenido").getAsString();
        task.time = json.has("time") ? json.get("time").getAsString() : "";
        
        return task;
    }

    private static void validateRequiredFields(JsonObject json) {
        if (!json.has("titulo") || !json.has("date") || !json.has("contenido")) {
            throw new IllegalStateException("El JSON no contiene todos los campos requeridos");
        }
    }

    public String getTitulo() { return titulo; }
    public String getDate() { return date; }
    public String getContenido() { return contenido; }
    public String getTime() { return time; }

    @Override
    public String toString() {
        return String.format("""
            ════════════════════════════════════════
            Título: %s
            Fecha: %s
            Time: %s
            Contenido:%s
            ════════════════════════════════════════""",
            titulo,
            date,
            time,
            contenido.replace("\n", "\n║ "));
    }
} 