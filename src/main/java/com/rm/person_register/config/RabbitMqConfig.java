package com.rm.person_register.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue personRegisterQueue(@Value("${rabbitmq.queues.person-register}") String queueName) {
        return new Queue(queueName, true);
    }
}
