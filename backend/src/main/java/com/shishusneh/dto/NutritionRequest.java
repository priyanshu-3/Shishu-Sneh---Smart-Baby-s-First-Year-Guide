package com.shishusneh.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Request DTO for the AI-powered nutrition endpoint.
 * The client sends the baby's age (in months) and a list of
 * locally available ingredients to get a personalized recipe.
 */
public class NutritionRequest {

    @NotNull(message = "Baby's age in months is required")
    @Min(value = 0, message = "Age must be 0 or more months")
    private Integer ageInMonths;

    @NotEmpty(message = "At least one ingredient is required")
    private List<String> ingredients;

    public NutritionRequest() {
    }

    public NutritionRequest(Integer ageInMonths, List<String> ingredients) {
        this.ageInMonths = ageInMonths;
        this.ingredients = ingredients;
    }

    public Integer getAgeInMonths() { return ageInMonths; }
    public void setAgeInMonths(Integer ageInMonths) { this.ageInMonths = ageInMonths; }

    public List<String> getIngredients() { return ingredients; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }
}
