package com.example;

import com.example.message.Message;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static com.example.util.JsonUtils.MAPPER;
import static org.assertj.core.api.Assertions.assertThat;

@EnableKafka
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = KafkaApplication.class)
//@ComponentScan("com.example")
public class KafkaApplicationTest {

    @Autowired
    private static final String TOPIC = "test-topic";
    private static final String TOPIC2 = "T_xArmorSDP_dbTraffic";

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    private String receivedMessage;

    @Test
    @DirtiesContext
    public void testKafkaListener() throws InterruptedException {
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
        System.out.println(message);

        // Wait for the message to be received
        Thread.sleep(2000);

        assertThat(receivedMessage).isEqualTo(message);
    }

    @KafkaListener(topics = TOPIC, groupId = "test-group",  topicPartitions  = {
            @TopicPartition(topic = TOPIC, partitions = { "0" })
    })
    public void listen(String message) {
        Message msg = readValue(message, Message.class);
        if (msg != null) {
            System.out.println("get obj");
        }
        receivedMessage = message;
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
