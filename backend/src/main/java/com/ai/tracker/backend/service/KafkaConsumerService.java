package com.ai.tracker.backend.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "learning-records", groupId = "ai-learning-group")
    public void consume(ConsumerRecord<String, String> record) {
        System.out.println("🟢 Consumed Kafka Message:");
        System.out.println("Key: " + record.key());
        System.out.println("Value: " + record.value());
        System.out.println("Partition: " + record.partition() + ", Offset: " + record.offset());
    }
}
