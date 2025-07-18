package com.ai.tracker.backend.model;

import java.time.LocalDate;

public class LearningRecord {
    private Long id;
    private String topic;
    private String notes;
    private LocalDate date;

    // Constructors
    public LearningRecord() {}

    public LearningRecord(Long id, String topic, String notes, LocalDate date) {
        this.id = id;
        this.topic = topic;
        this.notes = notes;
        this.date = date;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
