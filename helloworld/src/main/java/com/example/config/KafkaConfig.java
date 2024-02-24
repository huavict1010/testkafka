package com.example.config;


import com.example.receiver.KafkaReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public KafkaReceiver listener() {
        return new KafkaReceiver();
    }

}
