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
        int age = request.getAgeInMonths();
        List<String> ingredients = request.getIngredients();
        
        if (age < 6) {
            return new NutritionResponse(age, 
                "Dear Mother, 👶\n\nSince your baby is under 6 months old, it is strongly recommended to exclusively breastfeed. Do NOT introduce any solid foods yet. Breast milk provides all the nutrition and hydration your baby needs.\n\n" +
                "ಪ್ರಿಯ ತಾಯಿಯೇ, 👶\n\nನಿಮ್ಮ ಮಗುವಿಗೆ 6 ತಿಂಗಳಿಗಿಂತ ಕಡಿಮೆ ವಯಸ್ಸಾಗಿರುವುದರಿಂದ, ಕೇವಲ ಎದೆಹಾಲು ಮಾತ್ರ ನೀಡುವುದು ಅತ್ಯಂತ ಸೂಕ್ತ. ದಯವಿಟ್ಟು ಬೇರೆ ಯಾವುದೇ ಘನ ಆಹಾರಗಳನ್ನು ನೀಡಬೇಡಿ. ಎದೆಹಾಲಿನಲ್ಲಿ ನಿಮ್ಮ ಮಗುವಿಗೆ ಬೇಕಾದ ಎಲ್ಲಾ ಪೌಷ್ಟಿಕಾಂಶಗಳು ಮತ್ತು ನೀರಿನಂಶ ಇರುತ್ತದೆ."
            );
        }

        if (ingredients.isEmpty()) {
            ingredients = List.of("Rice");
        }

        try {
            String prompt = buildPrompt(age, ingredients);
            Map<String, Object> requestBody = Map.of(
                    "contents", List.of(
                            Map.of("parts", List.of(
                                    Map.of("text", prompt)
                            ))
                    )
            );

            @SuppressWarnings("unchecked")
            Map<String, Object> response = webClient.post()
                    .uri("?key=" + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(java.time.Duration.ofSeconds(5))
                    .block();

            String generatedRecipe = extractTextFromResponse(response);
            
            if (generatedRecipe != null && !generatedRecipe.startsWith("Unable to parse")) {
                return new NutritionResponse(age, generatedRecipe);
            }
        } catch (Exception e) {
            log.warn("Failed to generate AI response, falling back to demo response. Error: {}", e.getMessage());
        }

        String titleEng = String.join(" & ", ingredients) + " Mash/Porridge";
        String titleKan = String.join(" & ", ingredients) + " ಮಿಶ್ರಣ (Mash)";
        
        String prepEng = "   a. Wash and clean the following: " + String.join(", ", ingredients) + ".\n" +
                         "   b. Boil or steam them together until completely soft.\n" +
                         "   c. Mash completely into a smooth paste without any lumps, adding a little breastmilk or warm water if needed.";
        
        String prepKan = "   a. ಇವುಗಳನ್ನು ಚೆನ್ನಾಗಿ ತೊಳೆಯಿರಿ: " + String.join(", ", ingredients) + ".\n" +
                         "   b. ಸಂಪೂರ್ಣವಾಗಿ ಮೃದುವಾಗುವವರೆಗೆ ಒಟ್ಟಿಗೆ ಬೇಯಿಸಿ.\n" +
                         "   c. ಯಾವುದೇ ಗಂಟುಗಳಿಲ್ಲದಂತೆ ಚೆನ್ನಾಗಿ ಮಸಿಯಿರಿ, ಅಗತ್ಯವಿದ್ದರೆ ಸ್ವಲ್ಪ ಎದೆಹಾಲು ಅಥವಾ ಬೆಚ್ಚಗಿನ ನೀರನ್ನು ಸೇರಿಸಿ.";
                         
        String recipe = "1. 🍲 " + titleEng.toUpperCase() + "\n\n" +
                        "2. HOW TO MAKE IT 📝\n" + prepEng + "\n\n" +
                        "3. GOODNESS FOR BABY 🌟\n" +
                        "   This combination provides a balanced mix of energy, vitamins, and essential nutrients for early growth.\n\n" +
                        "4. SAFETY ⚠️\n" +
                        "   Ensure it's completely smooth and cool before feeding. Do not add salt or sugar.\n\n" +
                        "5. SERVING 🍼\n" +
                        "   Start with 2-3 spoons once a day.\n\n" +
                        "---\n\n" +
                        "1. 🍲 " + titleKan + "\n\n" +
                        "2. ಮಾಡುವ ವಿಧಾನ 📝\n" + prepKan + "\n\n" +
                        "3. ಮಗುವಿಗೆ ಉಪಯೋಗ 🌟\n" +
                        "   ಈ ಮಿಶ್ರಣವು ಮಗುವಿನ ಬೆಳವಣಿಗೆಗೆ ಶಕ್ತಿ ಮತ್ತು ವಿಟಮಿನ್‌ಗಳನ್ನು ಒದಗಿಸುತ್ತದೆ.\n\n" +
                        "4. ಸುರಕ್ಷತೆ ⚠️\n" +
                        "   ತಿನ್ನಿಸುವ ಮುನ್ನ ಗಂಜಿ ತಣ್ಣಗಾಗಿದೆಯೇ ಮತ್ತು ನಯವಾಗಿದೆಯೇ ಎಂದು ಖಚಿತಪಡಿಸಿಕೊಳ್ಳಿ. ಉಪ್ಪು ಅಥವಾ ಸಕ್ಕರೆ ಸೇರಿಸಬೇಡಿ.\n\n" +
                        "5. ಪ್ರಮಾಣ 🍼\n" +
                        "   ದಿನಕ್ಕೆ ಒಮ್ಮೆ 2-3 ಚಮಚ ನೀಡಿ.";

        return new NutritionResponse(age, recipe);
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
                
                CRITICAL FORMATTING INSTRUCTIONS:
                - Do NOT use ANY markdown characters such as asterisks (*), bold formatting (**), or hashes (#).
                - Use plain text only.
                - Use emojis to make the text look beautiful, friendly, and professional.
                - Separate sections with blank lines for readability.
                - First, provide the complete response in ENGLISH.
                - Then, provide the complete translation of the response in KANNADA.
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
