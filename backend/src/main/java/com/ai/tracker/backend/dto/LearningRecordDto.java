package com.ai.tracker.backend.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class LearningRecordDto {
    private String topic;
    private String notes;

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
