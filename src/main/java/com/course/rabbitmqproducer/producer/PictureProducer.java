package com.course.rabbitmqproducer.producer;

import com.course.rabbitmqproducer.model.Picture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PictureProducer {

    Logger logger = LoggerFactory.getLogger(PictureProducer.class);

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    public PictureProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendPicture(Picture picture) throws JsonProcessingException {

        var json = objectMapper.writeValueAsString(picture);

        logger.info("Picture message: {}", json);

        rabbitTemplate.convertAndSend("x.picture", picture.getType(), json);
    }
}
