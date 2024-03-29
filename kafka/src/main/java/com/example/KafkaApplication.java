package com.example;

import com.example.message.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static com.example.util.JsonUtils.MAPPER;
@Slf4j
@SpringBootApplication
public class KafkaApplication implements CommandLineRunner {

    private static final String TOPIC = "test-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 发送消息到名为"example-topic"的Kafka主题
//        kafkaTemplate.send("test-topic", "Hello, Kafka!");
        String message = "{\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"dbTrafficId\": \"186370029\", \n" +
                "            \"cluster\": \"jdbc:mysql//xxx\", \n" +
                "            \"sql\": \"SELECT * FROM x_xxx_t\", \n" +
                "            \"columns\": [\n" +
                "                {\n" +
                "                    \"databaseName\": \"BIDM\", \n" +
                "                    \"tableName\": \"DM_SD_IMP_TASK_F\", \n" +
                "                    \"fieldName\": \"CREATED_BY\" \n" +
                "                },\n" +
                "                {\n" +
                "                    \"databaseName\": \"BIDM\",\n" +
                "                    \"tableName\": \"api\",\n" +
                "                    \"fieldName\": \"/damd/api/sss\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"rows\": 1000, \n" +
                "            \"errorMsg\": \"\" \n" +
                "        }\n" +
                "    ],\n" +
                "    \"tenant_id\": \"00000000000000000000000000000000\",\n" +
                "    \"sub_tenant_id\": \"\",\n" +
                "    \"app_id\": \"com.huawei.sd.isdp.sdimp\",\n" +
                "    \"agent_id\": \"11892\"\n" +
                "}";
        kafkaTemplate.send(TOPIC,0, UUID.randomUUID().toString(), message);
//        System.out.println(message);

        // Wait for the message to be received
        Thread.sleep(2000);

    }

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void listen(String message) {
        System.out.println("Received Message in group 'example-group': " + message);
        Message msg = readValue(message, Message.class);
        if (msg != null) {
            System.out.println("get obj:" + msg.getAppId());
        }
    }

    protected <T> T readValue(String value, Class<T> classType) {
        try {
            return MAPPER.readValue(value, classType);
        } catch (JsonProcessingException ex) {
            log.error("json readValue is error value = {}", value, ex);
        }
        return null;
    }
}

