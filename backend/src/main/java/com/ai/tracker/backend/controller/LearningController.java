package com.ai.tracker.backend.controller;

import com.ai.tracker.backend.dto.LearningRecordDto;
import com.ai.tracker.backend.model.LearningRecord;
import com.ai.tracker.backend.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class LearningController {

    private final LearningService learningService;

    @Autowired
    public LearningController(LearningService learningService) {
        this.learningService = learningService;
    }

    @PostMapping
    public LearningRecord addRecord(@RequestBody LearningRecordDto dto) {
        System.out.println("Received DTO: " + dto);
        return learningService.addRecord(dto);
    }

    @GetMapping
    public List<LearningRecord> getAllRecords() {
        return learningService.getAllRecords();
    }
}
