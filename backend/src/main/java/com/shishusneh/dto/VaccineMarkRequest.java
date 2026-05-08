package com.shishusneh.dto;

import jakarta.validation.constraints.NotBlank;

public class VaccineMarkRequest {
    @NotBlank(message = "Vaccine name is required")
    private String vaccineName;
    private boolean completed;

    public VaccineMarkRequest() {}

    public String getVaccineName() { return vaccineName; }
    public void setVaccineName(String vaccineName) { this.vaccineName = vaccineName; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
