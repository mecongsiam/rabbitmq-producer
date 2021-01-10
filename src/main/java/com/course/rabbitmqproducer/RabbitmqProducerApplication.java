package com.course.rabbitmqproducer;

import com.course.rabbitmqproducer.model.Employee;
import com.course.rabbitmqproducer.producer.EmployeeJsonProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

@SpringBootApplication
@EnableScheduling
public class RabbitmqProducerApplication implements CommandLineRunner {

    private final EmployeeJsonProducer employeeJsonProducer;

    public RabbitmqProducerApplication(EmployeeJsonProducer employeeJsonProducer) {
        this.employeeJsonProducer = employeeJsonProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 5; i++) {
            employeeJsonProducer.sendMessage(new Employee(Integer.toString(i), "Employee " + i,
                    LocalDate.now()));
        }
    }

}
