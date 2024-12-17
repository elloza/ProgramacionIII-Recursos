package com.elloza.ollamawhisperexample;

import com.google.gson.JsonObject;

public class LLMResponse {
    private final String rawResponse;
    private final JsonObject jsonResponse;

    public LLMResponse(String rawResponse, JsonObject jsonResponse) {
        this.rawResponse = rawResponse;
        this.jsonResponse = jsonResponse;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public JsonObject getJsonResponse() {
        return jsonResponse;
    }
} 