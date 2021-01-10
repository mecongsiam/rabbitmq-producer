package com.course.rabbitmqproducer;

import com.course.rabbitmqproducer.model.Employee;
import com.course.rabbitmqproducer.model.Picture;
import com.course.rabbitmqproducer.producer.EmployeeJsonProducer;
import com.course.rabbitmqproducer.producer.PictureProducer;
import com.course.rabbitmqproducer.producer.PictureTopicExchangeProducer;
import com.course.rabbitmqproducer.producer.ResourceJsonProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
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

    private List<String> SOURCES= List.of("mobile", "web");
    private List<String> TYPES= List.of("jpg", "svg", "png");

    public RabbitmqProducerApplication(EmployeeJsonProducer employeeJsonProducer, ResourceJsonProducer resourceJsonProducer, PictureProducer pictureProducer, PictureTopicExchangeProducer pictureTopicExchangeProducer) {
        this.employeeJsonProducer = employeeJsonProducer;
        this.resourceJsonProducer = resourceJsonProducer;
        this.pictureProducer = pictureProducer;
        this.pictureTopicExchangeProducer = pictureTopicExchangeProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 5; i++) {
            employeeJsonProducer.sendMessage(new Employee(Integer.toString(i), "Employee " + i,
                    LocalDate.now()));
            resourceJsonProducer.sendMessage(new Employee(Integer.toString(i), "Employee " + i,
                    LocalDate.now()));
        }
        for (int i = 0; i < 10; i++){
            var picture = new Picture();

            picture.setName("Picture "+ i);
            picture.setSource(SOURCES.get(i%SOURCES.size()));
            picture.setType(TYPES.get(i%TYPES.size()));
            picture.setSize(ThreadLocalRandom.current().nextLong(1,10001));
            logger.info("Picture: {}", picture);
            pictureProducer.sendPicture(picture);
            pictureTopicExchangeProducer.sendPicture(picture);

        }
    }

}
