package com.shishusneh.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

/**
 * Entity representing a single health check-up entry.
 * Tracks weight, height, and developmental milestones
 * to power growth chart visualizations on the Android client.
 */
@Entity
@Table(name = "health_logs")
public class HealthLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Log date is required")
    @Column(nullable = false)
    private LocalDate date;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    @Column(nullable = false)
    private Double weight; // in kilograms

    @Positive(message = "Height must be positive")
    private Double height; // in centimeters (optional)

    @Column(name = "milestone_achieved", length = 500)
    private String milestoneAchieved; // e.g., "First smile", "Holds head up"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baby_profile_id", nullable = false)
    @JsonIgnore
    private BabyProfile babyProfile;

    public HealthLog() {
    }

    public HealthLog(Long id, LocalDate date, Double weight, Double height, String milestoneAchieved) {
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.milestoneAchieved = milestoneAchieved;
    }

    // ── Getters & Setters ──

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }

    public String getMilestoneAchieved() { return milestoneAchieved; }
    public void setMilestoneAchieved(String milestoneAchieved) { this.milestoneAchieved = milestoneAchieved; }

    public BabyProfile getBabyProfile() { return babyProfile; }
    public void setBabyProfile(BabyProfile babyProfile) { this.babyProfile = babyProfile; }
}
