package com.course.rabbitmqproducer.producer;

import com.course.rabbitmqproducer.model.Picture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PictureTopicExchangeProducer {

    Logger logger = LoggerFactory.getLogger(PictureTopicExchangeProducer.class);

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    public PictureTopicExchangeProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendPicture(Picture picture) throws JsonProcessingException {

        var json = objectMapper.writeValueAsString(picture);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(picture.getSource());
        stringBuilder.append(".");
        if (picture.getSize() > 4000) {
            stringBuilder.append("large");
        } else {
            stringBuilder.append("small");
        }
        stringBuilder.append(".");
        stringBuilder.append(picture.getType());

        String routingKey = stringBuilder.toString();

        logger.info("Picture message: {}", json);

        rabbitTemplate.convertAndSend("x.picture2", routingKey, json);
    }
}
