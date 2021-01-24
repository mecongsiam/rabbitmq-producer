package com.course.rabbitmqproducer.producer;

import com.course.rabbitmqproducer.model.InvoiceCancelledMessage;
import com.course.rabbitmqproducer.model.InvoiceCreatedMessage;
import com.course.rabbitmqproducer.model.InvoicePaidMessage;
import com.course.rabbitmqproducer.model.InvoiceRejectedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private static final String EXCHANGE = "x.invoice";

	public void sendInvoiceCreated(InvoiceCreatedMessage message) {
		rabbitTemplate.convertAndSend(EXCHANGE, message.getInvoiceNumber(), message);
	}

	public void sendInvoicePaid(InvoicePaidMessage message) {
		rabbitTemplate.convertAndSend(EXCHANGE, "", message);
	}

	public void sendInvoiceCancelled(InvoiceCancelledMessage message) {
		rabbitTemplate.convertAndSend(EXCHANGE, "", message);
	}

	public void sendInvoiceRejected(InvoiceRejectedMessage message) {
		rabbitTemplate.convertAndSend(EXCHANGE, "", message);
	}

}
