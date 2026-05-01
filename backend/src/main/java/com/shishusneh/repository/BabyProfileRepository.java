package com.shishusneh.repository;

import com.shishusneh.model.BabyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for BabyProfile CRUD operations.
 */
@Repository
public interface BabyProfileRepository extends JpaRepository<BabyProfile, Long> {
}
