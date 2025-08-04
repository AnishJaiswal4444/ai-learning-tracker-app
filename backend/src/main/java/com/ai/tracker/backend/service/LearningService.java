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

    @Autowired
    private KafkaProducerService kafkaProducer;

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
        LearningRecord saved = repository.save(record);

        // Send message to Kafka
        String kafkaMessage = "New learning record added: " + saved.getTopic();
        kafkaProducer.sendMessage("learning-records", kafkaMessage);

        return saved;
    }

    public List<LearningRecord> getAllRecords() {
        return repository.findAll();
    }
}
