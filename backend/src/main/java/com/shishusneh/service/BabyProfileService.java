package com.shishusneh.service;

import com.shishusneh.model.BabyProfile;
import com.shishusneh.model.HealthLog;
import com.shishusneh.repository.BabyProfileRepository;
import com.shishusneh.repository.HealthLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing baby profiles and health logs.
 */
@Service
public class BabyProfileService {

    private final BabyProfileRepository babyProfileRepository;
    private final HealthLogRepository healthLogRepository;

    public BabyProfileService(BabyProfileRepository babyProfileRepository,
                              HealthLogRepository healthLogRepository) {
        this.babyProfileRepository = babyProfileRepository;
        this.healthLogRepository = healthLogRepository;
    }

    /**
     * Register a new baby profile.
     */
    public BabyProfile registerBaby(BabyProfile profile) {
        return babyProfileRepository.save(profile);
    }

    /**
     * Retrieve a baby profile by ID.
     *
     * @throws RuntimeException if profile not found
     */
    public BabyProfile getBabyById(Long id) {
        return babyProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Baby profile not found with id: " + id));
    }

    /**
     * Get all registered baby profiles.
     */
    public List<BabyProfile> getAllBabies() {
        return babyProfileRepository.findAll();
    }

    /**
     * Add a health log entry for a specific baby.
     */
    public HealthLog addHealthLog(Long babyId, HealthLog healthLog) {
        BabyProfile profile = getBabyById(babyId);
        healthLog.setBabyProfile(profile);
        return healthLogRepository.save(healthLog);
    }

    /**
     * Get all health logs for a baby, ordered by date (for growth charts).
     */
    public List<HealthLog> getHealthLogs(Long babyId) {
        return healthLogRepository.findByBabyProfileIdOrderByDateAsc(babyId);
    }
}
