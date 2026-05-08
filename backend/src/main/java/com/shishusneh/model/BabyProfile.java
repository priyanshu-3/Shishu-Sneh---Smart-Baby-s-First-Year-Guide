package com.shishusneh.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a registered baby profile.
 * Stores core identity and birth metrics used for
 * vaccination scheduling and growth tracking.
 */
@Entity
@Table(name = "baby_profiles")
public class BabyProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Baby name is required")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull(message = "Birth weight is required")
    @Positive(message = "Birth weight must be positive")
    @Column(name = "birth_weight", nullable = false)
    private Double birthWeight; // in kilograms

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "babyProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<HealthLog> healthLogs = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "baby_completed_vaccines", joinColumns = @JoinColumn(name = "baby_id"))
    @Column(name = "vaccine_name")
    private List<String> completedVaccines = new ArrayList<>();

    public BabyProfile() {
    }

    public BabyProfile(Long id, String name, LocalDate dateOfBirth, Double birthWeight) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.birthWeight = birthWeight;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ── Getters & Setters ──

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Double getBirthWeight() { return birthWeight; }
    public void setBirthWeight(Double birthWeight) { this.birthWeight = birthWeight; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<HealthLog> getHealthLogs() { return healthLogs; }
    public void setHealthLogs(List<HealthLog> healthLogs) { this.healthLogs = healthLogs; }

    public List<String> getCompletedVaccines() { return completedVaccines; }
    public void setCompletedVaccines(List<String> completedVaccines) { this.completedVaccines = completedVaccines; }
}
