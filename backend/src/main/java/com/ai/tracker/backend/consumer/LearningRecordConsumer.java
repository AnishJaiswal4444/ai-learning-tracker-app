package com.ai.tracker.backend.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LearningRecordConsumer {

    @KafkaListener(topics = "learning-records", groupId = "learning-record-group")
    public void consume(ConsumerRecord<String, String> record) {
        System.out.println("🟢 Consumed Kafka Message:");
        System.out.println("Key: " + record.key());
        System.out.println("Value: " + record.value());
        System.out.println("Partition: " + record.partition() + ", Offset: " + record.offset());
    }
}
