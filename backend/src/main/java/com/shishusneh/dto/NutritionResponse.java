package com.shishusneh.dto;

/**
 * Response DTO wrapping the AI-generated nutritional guidance.
 */
public class NutritionResponse {

    private Integer ageInMonths;
    private String feedingGuide;

    public NutritionResponse() {
    }

    public NutritionResponse(Integer ageInMonths, String feedingGuide) {
        this.ageInMonths = ageInMonths;
        this.feedingGuide = feedingGuide;
    }

    public Integer getAgeInMonths() { return ageInMonths; }
    public void setAgeInMonths(Integer ageInMonths) { this.ageInMonths = ageInMonths; }

    public String getFeedingGuide() { return feedingGuide; }
    public void setFeedingGuide(String feedingGuide) { this.feedingGuide = feedingGuide; }
}
