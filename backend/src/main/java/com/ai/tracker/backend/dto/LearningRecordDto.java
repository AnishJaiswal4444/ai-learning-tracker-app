package com.ai.tracker.backend.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LearningRecordDto {
    @NotBlank(message = "Topic must not be empty")
    private String topic;
    @NotBlank(message = "Notes must not be empty")
    private String notes;
    @NotNull(message = "Date must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    // Constructors
    public LearningRecordDto() {}

    public LearningRecordDto(String topic, String notes, LocalDate date) {
        this.topic = topic;
        this.notes = notes;
        this.date = date;
    }

    // Getters and Setters
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "LearningRecordDto{" +
                "topic='" + topic + '\'' +
                ", notes='" + notes + '\'' +
                ", date=" + date +
                '}';
    }
}
