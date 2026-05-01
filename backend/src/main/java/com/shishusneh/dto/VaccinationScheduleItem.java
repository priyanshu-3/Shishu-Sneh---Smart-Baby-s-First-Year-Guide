package com.shishusneh.dto;

import java.time.LocalDate;

/**
 * DTO representing a single vaccination entry in the schedule.
 * Each item contains the vaccine name, when it is due,
 * the recommended age window, and a brief description of its purpose.
 */
public class VaccinationScheduleItem {

    private String vaccineName;
    private String dueAt;          // e.g., "At Birth", "6 Weeks"
    private LocalDate dueDate;
    private String purpose;
    private boolean completed;     // can be toggled by the client later

    public VaccinationScheduleItem() {
    }

    public VaccinationScheduleItem(String vaccineName, String dueAt, LocalDate dueDate, String purpose, boolean completed) {
        this.vaccineName = vaccineName;
        this.dueAt = dueAt;
        this.dueDate = dueDate;
        this.purpose = purpose;
        this.completed = completed;
    }

    public String getVaccineName() { return vaccineName; }
    public void setVaccineName(String vaccineName) { this.vaccineName = vaccineName; }

    public String getDueAt() { return dueAt; }
    public void setDueAt(String dueAt) { this.dueAt = dueAt; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
