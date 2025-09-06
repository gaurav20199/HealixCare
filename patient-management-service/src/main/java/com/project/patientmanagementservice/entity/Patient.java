package com.project.patientmanagementservice.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends BaseEntity {

    private UUID uuid;

    private String name;

    @Email
    private String email;

    private LocalDate dateOfBirth;

    // Not relying on createdAt as it is for auditing purpose, and it refers to when record will be inserted to DB.
    private LocalDate registered_date;

}
