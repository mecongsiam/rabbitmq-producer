package com.course.rabbitmqproducer.producer;

import com.course.rabbitmqproducer.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RetryEmployeeProducer {

	private final RabbitTemplate rabbitTemplate;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	public RetryEmployeeProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMessage(Employee emp) throws JsonProcessingException {
		var json = objectMapper.writeValueAsString(emp);
		rabbitTemplate.convertAndSend("x.guideline2.work", "", json);
	}
	
}
