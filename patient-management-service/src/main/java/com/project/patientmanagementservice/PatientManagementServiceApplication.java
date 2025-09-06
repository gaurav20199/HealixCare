package com.project.patientmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PatientManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientManagementServiceApplication.class, args);
    }

}
