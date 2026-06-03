package com.dashboard.backend.ai.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@Primary
public class GeminiAIService implements AIProviderService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    public GeminiAIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://generativelanguage.googleapis.com")
                .build();
    }

    @Override
    public String generateInsights(String prompt) {
        try {

            Map<String, Object> requestBody = Map.of(
                    "contents", List.of(
                            Map.of(
                                    "parts", List.of(
                                            Map.of("text", prompt)
                                    )
                            )
                    )
            );

            Map<String, Object> response = webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1beta/models/gemini-2.5-flash:generateContent")
                            .queryParam("key", apiKey)
                            .build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .retry(3)
                    .block();

            if (response == null) {
                return fallbackMessage();
            }

            List<Map<String, Object>> candidates =
                    (List<Map<String, Object>>) response.get("candidates");

            if (candidates == null || candidates.isEmpty()) {
                return fallbackMessage();
            }

            Map<String, Object> candidate = candidates.get(0);

            Map<String, Object> content =
                    (Map<String, Object>) candidate.get("content");

            if (content == null) {
                return fallbackMessage();
            }

            List<Map<String, Object>> parts =
                    (List<Map<String, Object>>) content.get("parts");

            if (parts == null || parts.isEmpty()) {
                return fallbackMessage();
            }

            Object text = parts.get(0).get("text");

            return text != null
                    ? text.toString()
                    : fallbackMessage();

        } catch (Exception ex) {
            System.err.println("Gemini API error: " + ex.getMessage());
            ex.printStackTrace();
            return fallbackMessage();
        }
    }

    private String fallbackMessage() {
        return """
                SUMMARY:
                AI insights temporarily unavailable.

                OBSERVATIONS:
                1. Analytics data was collected successfully.
                2. AI provider could not be reached.
                3. Retry later.

                RECOMMENDATIONS:
                1. Verify AI provider connectivity.
                2. Retry generating insights.
                """;
    }
}