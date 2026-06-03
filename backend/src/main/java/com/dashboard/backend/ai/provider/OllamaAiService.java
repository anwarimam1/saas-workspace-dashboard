//package com.dashboard.backend.ai.provider;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Service
//@Primary
//public class OllamaAiService implements AIProviderService {
//
//    private final WebClient webClient;
//
//    public OllamaAiService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.build();
//    }
//
//    @Override
//    public String generateInsights(String prompt) {
//        try {
//            Map<String, Object> request = new HashMap<>();
//            request.put("model", "llama3");
//            request.put("prompt", prompt);
//            request.put("stream", false);
//
//            Map<String, Object> response = webClient.post()
//                    .uri("http://localhost:11434/api/generate")
//                    .bodyValue(request)
//                    .retrieve()
//                    .bodyToMono(Map.class)
//                    .block();
//
//            if (response != null && response.get("response") != null) {
//                return response.get("response").toString();
//            }
//
//            return fallbackResponse();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return fallbackResponse();
//        }
//    }
//
//    private String fallbackResponse() {
//        return """
//                SUMMARY:
//                AI insights temporarily unavailable.
//
//                OBSERVATIONS:
//                1. Local AI provider could not be reached.
//                2. Retry later.
//
//                RECOMMENDATIONS:
//                1. Verify Ollama is running.
//                2. Ensure llama3 model is available.
//                """;
//    }
//}