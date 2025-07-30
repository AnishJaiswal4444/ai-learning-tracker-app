package com.ai.tracker.backend.service;

import com.ai.tracker.backend.dto.LearningRecordDto;
import com.ai.tracker.backend.model.LearningRecord;
import com.ai.tracker.backend.repository.LearningRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LearningService {

    private final LearningRecordRepository repository;
    @Autowired
    public LearningService(LearningRecordRepository repository) {
        this.repository = repository;
    }
    public LearningRecord addRecord(LearningRecordDto dto) {
        LearningRecord record = new LearningRecord(
                null,
                dto.getTopic(),
                dto.getNotes(),
                dto.getDate() != null ? dto.getDate() : LocalDate.now()
        );
        return repository.save(record);
    }

    public List<LearningRecord> getAllRecords() {
        return repository.findAll();
    }
}
