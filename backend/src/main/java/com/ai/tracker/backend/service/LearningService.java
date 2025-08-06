package com.ai.tracker.backend.service;

import com.ai.tracker.backend.dto.LearningRecordDto;
import com.ai.tracker.backend.model.LearningRecord;
import com.ai.tracker.backend.repository.LearningRecordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LearningService {

    @Autowired
    private KafkaProducerService kafkaProducer;

    private final LearningRecordRepository repository;
    private final ObjectMapper objectMapper;

    @Autowired
    public LearningService(LearningRecordRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }
    public LearningRecord addRecord(LearningRecordDto dto) {
        LearningRecord record = new LearningRecord(
                null,
                dto.getTopic(),
                dto.getNotes(),
                dto.getDate() != null ? dto.getDate() : LocalDate.now()
        );
        LearningRecord saved = repository.save(record);

        try {
            // Serialize DTO to JSON and send to Kafka
            String jsonMessage = objectMapper.writeValueAsString(dto);
            kafkaProducer.sendMessage("learning-records", jsonMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saved;
    }

    public List<LearningRecord> getAllRecords() {
        return repository.findAll();
    }
}
