package com.course.rabbitmqproducer.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class HelloRabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public HelloRabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sayHello(String name) {
        rabbitTemplate.convertAndSend("course.hello", name);
    }
}
