package com.shishusneh.service;

import com.shishusneh.dto.NutritionRequest;
import com.shishusneh.dto.NutritionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

/**
 * Service that integrates with the Google Gemini API to generate
 * personalized, culturally relevant feeding guides for babies
 * based on their age and locally available ingredients.
 *
 * Acts as a secure gateway — keeping the API key on the server
 * rather than exposing it in the Android client.
 */
@Service
public class GeminiNutritionService {

    private static final Logger log = LoggerFactory.getLogger(GeminiNutritionService.class);

    private final WebClient webClient;
    private final String apiKey;

    public GeminiNutritionService(
            @Value("${gemini.api.url}") String apiUrl,
            @Value("${gemini.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .build();
    }

    /**
     * Generate a feeding guide by sending a prompt to Gemini.
     *
     * @param request contains baby's age in months and list of local ingredients
     * @return NutritionResponse with the AI-generated feeding guide
     */
    public NutritionResponse generateFeedingGuide(NutritionRequest request) {
        String prompt = buildPrompt(request.getAgeInMonths(), request.getIngredients());

        try {
            // Build the Gemini API request body
            Map<String, Object> requestBody = Map.of(
                    "contents", List.of(
                            Map.of("parts", List.of(
                                    Map.of("text", prompt)
                            ))
                    )
            );

            // Call Gemini API
            @SuppressWarnings("unchecked")
            Map<String, Object> response = webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("key", apiKey)
                            .build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            // Extract the generated text from the response
            String generatedText = extractTextFromResponse(response);

            return new NutritionResponse(request.getAgeInMonths(), generatedText);

        } catch (Exception e) {
            log.error("Error calling Gemini API: {}", e.getMessage(), e);
            return new NutritionResponse(
                    request.getAgeInMonths(),
                    "Unable to generate feeding guide at this time. Please try again later. Error: " + e.getMessage()
            );
        }
    }

    /**
     * Build a detailed, pediatric-focused prompt for Gemini.
     */
    private String buildPrompt(int ageInMonths, List<String> ingredients) {
        String ingredientList = String.join(", ", ingredients);

        return String.format("""
                You are a pediatric nutritionist specializing in infant care in rural India.
                
                A mother has a baby who is %d months old. She has the following locally available \
                ingredients: %s.
                
                Please provide:
                1. A simple, nutritious recipe suitable for a %d-month-old baby using these ingredients.
                2. Step-by-step preparation instructions that are easy to follow.
                3. Nutritional benefits of the recipe for the baby's development.
                4. Any safety precautions or allergen warnings.
                5. Suggested serving size and frequency.
                
                Important guidelines:
                - If the baby is under 6 months, emphasize that ONLY breast milk or formula is recommended (exclusive breastfeeding). \
                Do NOT suggest solid foods for babies under 6 months.
                - Use simple language that a village mother can understand.
                - Keep the recipe practical with minimal cooking equipment.
                - Focus on locally available, affordable ingredients.
                
                Format the response in a clear, easy-to-read manner.
                """, ageInMonths, ingredientList, ageInMonths);
    }

    /**
     * Extract the generated text from Gemini's JSON response.
     */
    @SuppressWarnings("unchecked")
    private String extractTextFromResponse(Map<String, Object> response) {
        try {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                if (parts != null && !parts.isEmpty()) {
                    return (String) parts.get(0).get("text");
                }
            }
        } catch (Exception e) {
            log.error("Error parsing Gemini response: {}", e.getMessage());
        }
        return "Unable to parse the AI response. Please try again.";
    }
}
