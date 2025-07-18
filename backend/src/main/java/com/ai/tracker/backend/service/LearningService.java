package com.ai.tracker.backend.service;

import com.ai.tracker.backend.dto.LearningRecordDto;
import com.ai.tracker.backend.model.LearningRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LearningService {
    private final List<LearningRecord> records = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public LearningRecord addRecord(LearningRecordDto dto) {
        LearningRecord record = new LearningRecord(
                idGenerator.incrementAndGet(),
                dto.getTopic(),
                dto.getNotes(),
                dto.getDate() != null ? dto.getDate() : LocalDate.now()
        );
        records.add(record);
        return record;
    }

    public List<LearningRecord> getAllRecords() {
        return records;
    }
}
