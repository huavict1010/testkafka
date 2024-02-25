package com.example.service;

import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.model.Message;

@Service
public class KafkaConsumerService {

    @Autowired
    private MessageRepository messageRepository;

    @KafkaListener(topics = "your_topic_name", groupId = "test-group")
    public void consume(String content) {
        Message message = new Message();
        message.setContent(content);
        messageRepository.save(message);
    }
}