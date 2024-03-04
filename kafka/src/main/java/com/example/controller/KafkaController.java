package com.example.controller;

import com.example.sender.KafkaSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * kafka 测试接口
 */
@RestController
public class KafkaController {
    @Autowired
    private KafkaSender kafkaSender;

    @GetMapping("/sendMessageToKafka")
    public String sendMessageToKafka() {
        Map<String, String> messageMap = new HashMap();
        String message = "{    \"data\": [        {            \"dbTrafficId\": \"186370029\",            \"cluster\": \"jdbc:mysql//xxx\",             \"sql\": \"SELECT * FROM x_xxx_t\",             \"columns\": [                {                    \"databaseName\": \"damd\",                     \"tableName\": \"x_xxx_t\",                     \"fieldName\": \"id\"                 },                {                    \"databaseName\": \"dsp_xxx_t\",                    \"tableName\": \"api\",                    \"fieldName\": \"/damd/api/sss\"                }            ],            \"rows\": 1000,             \"errorMsg\": \"\"         }    ],    \"tenant_id\": \"00000000000000000000000000000000\",    \"sub_tenant_id\": \"\",    \"app_id\": \"app_000000035422\",    \"agent_id\": \"11892\"}";
        messageMap.put("message", message);
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

    @GetMapping("/getMessageFromKafka")
    public void getMessageFromKafka() {

    }
}
