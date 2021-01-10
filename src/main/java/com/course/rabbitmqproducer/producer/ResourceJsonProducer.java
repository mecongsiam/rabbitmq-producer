package com.course.rabbitmqproducer.producer;

import com.course.rabbitmqproducer.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResourceJsonProducer {

    Logger logger = LoggerFactory.getLogger(ResourceJsonProducer.class);

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    public ResourceJsonProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(Employee employee) throws JsonProcessingException {

        var json = objectMapper.writeValueAsString(employee);

        logger.info("Employee message: {}", json);

        rabbitTemplate.convertAndSend("x.hr", "",json);

    }
}


