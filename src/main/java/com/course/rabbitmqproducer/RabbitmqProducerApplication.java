package com.course.rabbitmqproducer;

import com.course.rabbitmqproducer.model.Picture;
import com.course.rabbitmqproducer.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@EnableScheduling
public class RabbitmqProducerApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(RabbitmqProducerApplication.class);

    private final EmployeeJsonProducer employeeJsonProducer;

    private final ResourceJsonProducer resourceJsonProducer;

    private final PictureProducer pictureProducer;

    private final PictureTopicExchangeProducer pictureTopicExchangeProducer;
    private final MyPictureProducer myPictureProducer;

    private final RetryPictureProducer retryPictureProducer;

    private List<String> SOURCES= List.of("mobile", "web");
    private List<String> TYPES= List.of("jpg", "svg", "png");

    public RabbitmqProducerApplication(EmployeeJsonProducer employeeJsonProducer, ResourceJsonProducer resourceJsonProducer, PictureProducer pictureProducer, PictureTopicExchangeProducer pictureTopicExchangeProducer, MyPictureProducer myPictureProducer, RetryPictureProducer retryPictureProducer) {
        this.employeeJsonProducer = employeeJsonProducer;
        this.resourceJsonProducer = resourceJsonProducer;
        this.pictureProducer = pictureProducer;
        this.pictureTopicExchangeProducer = pictureTopicExchangeProducer;
        this.myPictureProducer = myPictureProducer;
        this.retryPictureProducer = retryPictureProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            var p = new Picture();

            p.setName("Picture " + i);
            p.setSize(ThreadLocalRandom.current().nextLong(9001, 10001));
            p.setSource(SOURCES.get(i % SOURCES.size()));
            p.setType(TYPES.get(i % TYPES.size()));

            retryPictureProducer.sendMessage(p);
        }

    }

}
