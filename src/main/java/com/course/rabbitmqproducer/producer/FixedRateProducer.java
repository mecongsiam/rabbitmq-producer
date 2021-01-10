package com.course.rabbitmqproducer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FixedRateProducer {

    private Logger logger = LoggerFactory.getLogger(FixedRateProducer.class);

    private final RabbitTemplate rabbitTemplate;

    private int i;

    public FixedRateProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 500)
    public void PublishMessage(){
        i++;
        rabbitTemplate.convertAndSend("course.fixedrate", i);
        logger.info("Message : " + i);

    }


}
