package com.example.controller;

import com.example.sender.KafkaSender;
import com.example.service.KafkaProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.example.model.Message;

/**
 * kafka 测试接口
 */
@RestController
public class KafkaController {
    @Autowired
    private KafkaSender kafkaSender;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @GetMapping("/sendMessageToKafka")
    public String sendMessageToKafka() {
        Map<String, String> messageMap = new HashMap();
        messageMap.put("message", "hello world!");
        ObjectMapper objectMapper = new ObjectMapper();
        String data = null;
        try {
            data = objectMapper.writeValueAsString(messageMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String key = String.valueOf(UUID.randomUUID());
        //kakfa的推送消息方法有多种，可以采取带有任务key的，也可以采取不带有的（不带时默认为null）
        kafkaSender.send("testTopic", key, data);
        return "ok";
    }

    @GetMapping("/sendMessageGPT")
    public void sendMessage(@RequestBody Message message) {
        kafkaProducerService.sendMessage(message);
    }
}
