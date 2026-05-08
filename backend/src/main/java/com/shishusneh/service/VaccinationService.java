package com.shishusneh.service;

import com.shishusneh.dto.VaccinationScheduleItem;
import com.shishusneh.model.BabyProfile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service that dynamically calculates the vaccination schedule
 * based on the baby's date of birth, following the Indian
 * National Immunization Schedule (NIS).
 */
@Service
public class VaccinationService {

    /**
     * Generate the full first-year vaccination schedule for a baby.
     *
     * @param profile the baby's profile containing DOB
     * @return list of vaccination schedule items with calculated due dates
     */
    public List<VaccinationScheduleItem> getSchedule(BabyProfile profile) {
        LocalDate dob = profile.getDateOfBirth();
        List<VaccinationScheduleItem> schedule = new ArrayList<>();

        // ── At Birth ──
        schedule.add(new VaccinationScheduleItem(
                "BCG",
                "At Birth",
                dob,
                "Protects against Tuberculosis (TB). A single dose given at birth.",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "OPV-0 (Oral Polio Vaccine – Zero Dose)",
                "At Birth",
                dob,
                "First dose of oral polio vaccine to begin protection against Poliomyelitis.",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "Hepatitis B – Birth Dose",
                "At Birth",
                dob,
                "Prevents Hepatitis B virus infection. Must be given within 24 hours of birth.",
                false
        ));

        // ── 6 Weeks ──
        LocalDate sixWeeks = dob.plusWeeks(6);
        schedule.add(new VaccinationScheduleItem(
                "DPT-1 (Diphtheria, Pertussis, Tetanus)",
                "6 Weeks",
                sixWeeks,
                "First dose protecting against Diphtheria, Whooping Cough (Pertussis), and Tetanus.",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "IPV-1 (Injectable Polio Vaccine)",
                "6 Weeks",
                sixWeeks,
                "First injectable polio dose for enhanced immunity against Poliomyelitis.",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "Hepatitis B – 2nd Dose",
                "6 Weeks",
                sixWeeks,
                "Second dose of Hepatitis B vaccine to strengthen immunity.",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "Rotavirus-1",
                "6 Weeks",
                sixWeeks,
                "First dose to protect against Rotavirus gastroenteritis (severe diarrhea).",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "PCV-1 (Pneumococcal Conjugate Vaccine)",
                "6 Weeks",
                sixWeeks,
                "First dose protecting against Pneumococcal diseases (pneumonia, meningitis).",
                false
        ));

        // ── 10 Weeks ──
        LocalDate tenWeeks = dob.plusWeeks(10);
        schedule.add(new VaccinationScheduleItem(
                "DPT-2",
                "10 Weeks",
                tenWeeks,
                "Second dose of DPT for continued protection against Diphtheria, Pertussis, and Tetanus.",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "IPV-2",
                "10 Weeks",
                tenWeeks,
                "Second injectable polio dose.",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "Rotavirus-2",
                "10 Weeks",
                tenWeeks,
                "Second dose of Rotavirus vaccine.",
                false
        ));

        // ── 14 Weeks ──
        LocalDate fourteenWeeks = dob.plusWeeks(14);
        schedule.add(new VaccinationScheduleItem(
                "DPT-3",
                "14 Weeks",
                fourteenWeeks,
                "Third dose of DPT to complete the primary series.",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "IPV-3",
                "14 Weeks",
                fourteenWeeks,
                "Third injectable polio dose.",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "Hepatitis B – 3rd Dose",
                "14 Weeks",
                fourteenWeeks,
                "Third and final dose of Hepatitis B primary series.",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "Rotavirus-3",
                "14 Weeks",
                fourteenWeeks,
                "Third dose of Rotavirus vaccine (if using 3-dose schedule).",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "PCV-2",
                "14 Weeks",
                fourteenWeeks,
                "Second dose of Pneumococcal Conjugate Vaccine.",
                false
        ));

        // ── 6 Months ──
        LocalDate sixMonths = dob.plusMonths(6);
        schedule.add(new VaccinationScheduleItem(
                "OPV-1 (Oral Polio Vaccine – 1st Booster)",
                "6 Months",
                sixMonths,
                "Oral polio booster to maintain community-level polio immunity.",
                false
        ));

        // ── 9 Months ──
        LocalDate nineMonths = dob.plusMonths(9);
        schedule.add(new VaccinationScheduleItem(
                "MR-1 (Measles-Rubella – 1st Dose)",
                "9 Months",
                nineMonths,
                "First dose protecting against Measles and Rubella.",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "PCV Booster",
                "9 Months",
                nineMonths,
                "Booster dose of Pneumococcal vaccine for long-lasting protection.",
                false
        ));
        schedule.add(new VaccinationScheduleItem(
                "Vitamin A – 1st Dose",
                "9 Months",
                nineMonths,
                "First supplemental dose of Vitamin A to support immune function and vision.",
                false
        ));

        // ── 12 Months ──
        LocalDate twelveMonths = dob.plusMonths(12);
        schedule.add(new VaccinationScheduleItem(
                "Hepatitis A – 1st Dose",
                "12 Months",
                twelveMonths,
                "Protection against Hepatitis A virus infection.",
                false
        ));

        // Update completion status based on user's profile
        List<String> completedVaccines = profile.getCompletedVaccines();
        if (completedVaccines != null) {
            for (VaccinationScheduleItem item : schedule) {
                if (completedVaccines.contains(item.getVaccineName())) {
                    item.setCompleted(true);
                }
            }
        }

        return schedule;
    }
}
