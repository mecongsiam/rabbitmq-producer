package com.course.rabbitmqproducer;

import com.course.rabbitmqproducer.model.Employee;
import com.course.rabbitmqproducer.producer.EmployeeJsonProducer;
import com.course.rabbitmqproducer.producer.ResourceJsonProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

@SpringBootApplication
@EnableScheduling
public class RabbitmqProducerApplication implements CommandLineRunner {

    private final EmployeeJsonProducer employeeJsonProducer;

    private final ResourceJsonProducer resourceJsonProducer;

    public RabbitmqProducerApplication(EmployeeJsonProducer employeeJsonProducer, ResourceJsonProducer resourceJsonProducer) {
        this.employeeJsonProducer = employeeJsonProducer;
        this.resourceJsonProducer = resourceJsonProducer;
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
    }

}
