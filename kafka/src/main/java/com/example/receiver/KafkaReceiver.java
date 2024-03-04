package com.example.receiver;

import com.example.message.Message;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class KafkaReceiver {
    /**
     * 下面的主题是一个数组，可以同时订阅多主题，只需按数组格式即可，也就是用","隔开
     */
    @KafkaListener(topics = {"testTopic"})
    public void receive(ConsumerRecord<?, ?> record){
        log.info("消费者收到的消息key: " + record.key());
        log.info("消费者收到的消息value: " + record.value().toString());
        if (record != null && record.value() != null && !record.value().equals("{\"message\":\"hello world!\"}")) {
            System.out.println(reader((String) record.value()));
        }
    }

    public Message reader(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Message msg = objectMapper.readValue(message, Message.class);
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
