package com.course.rabbitmqproducer;

import com.course.rabbitmqproducer.model.*;
import com.course.rabbitmqproducer.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
//@EnableScheduling
public class RabbitmqProducerApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(RabbitmqProducerApplication.class);

    private final EmployeeJsonProducer employeeJsonProducer;

    private final ResourceJsonProducer resourceJsonProducer;

    private final PictureProducer pictureProducer;

    private final PictureTopicExchangeProducer pictureTopicExchangeProducer;
    private final MyPictureProducer myPictureProducer;

    private final RetryPictureProducer retryPictureProducer;

    private final RetryEmployeeProducer retryEmployeeProducer;

    private final InvoiceProducer invoiceProducer;

    private List<String> SOURCES= List.of("mobile", "web");
    private List<String> TYPES= List.of("jpg", "svg", "png");

    public RabbitmqProducerApplication(EmployeeJsonProducer employeeJsonProducer, ResourceJsonProducer resourceJsonProducer, PictureProducer pictureProducer, PictureTopicExchangeProducer pictureTopicExchangeProducer, MyPictureProducer myPictureProducer, RetryPictureProducer retryPictureProducer, RetryEmployeeProducer retryEmployeeProducer, InvoiceProducer invoiceProducer) {
        this.employeeJsonProducer = employeeJsonProducer;
        this.resourceJsonProducer = resourceJsonProducer;
        this.pictureProducer = pictureProducer;
        this.pictureTopicExchangeProducer = pictureTopicExchangeProducer;
        this.myPictureProducer = myPictureProducer;
        this.retryPictureProducer = retryPictureProducer;
        this.retryEmployeeProducer = retryEmployeeProducer;
        this.invoiceProducer = invoiceProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        var invoiceCreatedMessage = new InvoiceCreatedMessage(ThreadLocalRandom.current().nextInt(100), LocalDate.now(), "USD",
                "1");
        invoiceProducer.sendInvoiceCreated(invoiceCreatedMessage);

        var invoicePaid = new InvoicePaidMessage("1", LocalDate.now(),
                "2");
        invoiceProducer.sendInvoicePaid(invoicePaid);

        var invoiceRejectedMessage = new InvoiceRejectedMessage(LocalDate.now(),"1", "reason");
        invoiceProducer.sendInvoiceRejected(invoiceRejectedMessage);

        var invoiceCanceledMessage = new InvoiceCancelledMessage(LocalDate.now(),"1","reason");
        invoiceProducer.sendInvoiceCancelled(invoiceCanceledMessage);

    }

}
