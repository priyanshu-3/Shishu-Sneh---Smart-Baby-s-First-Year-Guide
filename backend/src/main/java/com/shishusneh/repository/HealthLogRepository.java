package com.shishusneh.repository;

import com.shishusneh.model.HealthLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for HealthLog CRUD operations.
 */
@Repository
public interface HealthLogRepository extends JpaRepository<HealthLog, Long> {

    /**
     * Find all health logs for a specific baby, ordered by date ascending.
     * This ordering is ideal for plotting growth trend lines.
     */
    List<HealthLog> findByBabyProfileIdOrderByDateAsc(Long babyProfileId);
}
