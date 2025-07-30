package com.ai.tracker.backend.repository;

import com.ai.tracker.backend.model.LearningRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningRecordRepository extends JpaRepository<LearningRecord, Long> {
}
