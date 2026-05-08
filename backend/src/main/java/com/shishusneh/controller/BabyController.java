package com.shishusneh.controller;

import com.shishusneh.dto.NutritionRequest;
import com.shishusneh.dto.NutritionResponse;
import com.shishusneh.dto.VaccinationScheduleItem;
import com.shishusneh.model.BabyProfile;
import com.shishusneh.model.HealthLog;
import com.shishusneh.service.BabyProfileService;
import com.shishusneh.service.GeminiNutritionService;
import com.shishusneh.service.VaccinationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller exposing the Shishu-Sneh API endpoints.
 *
 * Endpoints:
 *   POST /api/baby/register         → Register a new baby profile
 *   GET  /api/baby/{id}             → Get a baby profile by ID
 *   GET  /api/baby/{id}/vaccines    → Get the calculated vaccination schedule
 *   POST /api/baby/{id}/health-log  → Add a health log entry
 *   GET  /api/baby/{id}/health-logs → Get all health logs (for growth charts)
 *   POST /api/baby/nutrition        → Get AI-generated feeding guide
 */
@RestController
@RequestMapping("/api/baby")
@CrossOrigin(origins = "*") // Allow Android app requests
public class BabyController {

    private final BabyProfileService babyProfileService;
    private final VaccinationService vaccinationService;
    private final GeminiNutritionService geminiNutritionService;

    public BabyController(BabyProfileService babyProfileService,
                          VaccinationService vaccinationService,
                          GeminiNutritionService geminiNutritionService) {
        this.babyProfileService = babyProfileService;
        this.vaccinationService = vaccinationService;
        this.geminiNutritionService = geminiNutritionService;
    }

    // ═══════════════════════════════════════════
    //  Baby Profile Endpoints
    // ═══════════════════════════════════════════

    /**
     * Register a new baby profile.
     *
     * POST /api/baby/register
     * Body: { "name": "Arya", "dateOfBirth": "2025-03-15", "birthWeight": 3.2 }
     */
    @PostMapping("/register")
    public ResponseEntity<BabyProfile> registerBaby(@Valid @RequestBody BabyProfile profile) {
        BabyProfile saved = babyProfileService.registerBaby(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Get a baby profile by ID.
     *
     * GET /api/baby/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<BabyProfile> getBaby(@PathVariable Long id) {
        return ResponseEntity.ok(babyProfileService.getBabyById(id));
    }

    /**
     * Get all registered baby profiles.
     *
     * GET /api/baby/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<BabyProfile>> getAllBabies() {
        return ResponseEntity.ok(babyProfileService.getAllBabies());
    }

    // ═══════════════════════════════════════════
    //  Vaccination Schedule Endpoint
    // ═══════════════════════════════════════════

    /**
     * Get the dynamically calculated vaccination schedule for a baby.
     *
     * GET /api/baby/{id}/vaccines
     */
    @GetMapping("/{id}/vaccines")
    public ResponseEntity<List<VaccinationScheduleItem>> getVaccinationSchedule(@PathVariable Long id) {
        BabyProfile profile = babyProfileService.getBabyById(id);
        List<VaccinationScheduleItem> schedule = vaccinationService.getSchedule(profile);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Mark a vaccine as completed or undo completion.
     *
     * POST /api/baby/{id}/vaccines/mark
     * Body: { "vaccineName": "BCG", "completed": true }
     */
    @PostMapping("/{id}/vaccines/mark")
    public ResponseEntity<BabyProfile> markVaccine(
            @PathVariable Long id,
            @Valid @RequestBody com.shishusneh.dto.VaccineMarkRequest request) {
        BabyProfile updated = babyProfileService.toggleVaccineCompletion(id, request.getVaccineName(), request.isCompleted());
        return ResponseEntity.ok(updated);
    }

    // ═══════════════════════════════════════════
    //  Health Log Endpoints (Growth Tracking)
    // ═══════════════════════════════════════════

    /**
     * Add a health log entry for a baby.
     *
     * POST /api/baby/{id}/health-log
     * Body: { "date": "2025-04-15", "weight": 4.1, "height": 55.0, "milestoneAchieved": "First smile" }
     */
    @PostMapping("/{id}/health-log")
    public ResponseEntity<HealthLog> addHealthLog(
            @PathVariable Long id,
            @Valid @RequestBody HealthLog healthLog) {
        HealthLog saved = babyProfileService.addHealthLog(id, healthLog);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Get all health logs for a baby (ordered by date, for growth chart plotting).
     *
     * GET /api/baby/{id}/health-logs
     */
    @GetMapping("/{id}/health-logs")
    public ResponseEntity<List<HealthLog>> getHealthLogs(@PathVariable Long id) {
        return ResponseEntity.ok(babyProfileService.getHealthLogs(id));
    }

    // ═══════════════════════════════════════════
    //  AI-Powered Nutrition Endpoint
    // ═══════════════════════════════════════════

    /**
     * Generate a personalized feeding guide using Gemini AI.
     *
     * POST /api/baby/nutrition
     * Body: { "ageInMonths": 8, "ingredients": ["rice", "dal", "banana"] }
     */
    @PostMapping("/nutrition")
    public ResponseEntity<NutritionResponse> getNutritionGuide(
            @Valid @RequestBody NutritionRequest request) {
        NutritionResponse response = geminiNutritionService.generateFeedingGuide(request);
        return ResponseEntity.ok(response);
    }

    // ═══════════════════════════════════════════
    //  Health Check
    // ═══════════════════════════════════════════

    /**
     * Simple health check endpoint.
     *
     * GET /api/baby/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "application", "Shishu-Sneh Backend",
                "message", "Shishu-Sneh is running healthy!"
        ));
    }
}
