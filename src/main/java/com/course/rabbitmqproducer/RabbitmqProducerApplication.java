package com.course.rabbitmqproducer;

import com.course.rabbitmqproducer.model.Employee;
import com.course.rabbitmqproducer.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.List;

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

    private List<String> SOURCES= List.of("mobile", "web");
    private List<String> TYPES= List.of("jpg", "svg", "png");

    public RabbitmqProducerApplication(EmployeeJsonProducer employeeJsonProducer, ResourceJsonProducer resourceJsonProducer, PictureProducer pictureProducer, PictureTopicExchangeProducer pictureTopicExchangeProducer, MyPictureProducer myPictureProducer, RetryPictureProducer retryPictureProducer, RetryEmployeeProducer retryEmployeeProducer) {
        this.employeeJsonProducer = employeeJsonProducer;
        this.resourceJsonProducer = resourceJsonProducer;
        this.pictureProducer = pictureProducer;
        this.pictureTopicExchangeProducer = pictureTopicExchangeProducer;
        this.myPictureProducer = myPictureProducer;
        this.retryPictureProducer = retryPictureProducer;
        this.retryEmployeeProducer = retryEmployeeProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Employee emp = new Employee("Employee" + i, null, LocalDate.now());
            retryEmployeeProducer.sendMessage(emp);
        }
    }

}
