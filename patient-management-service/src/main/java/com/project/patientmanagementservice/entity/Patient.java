package com.project.patientmanagementservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patient extends BaseEntity {

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    private String name;

    @Email
    private String email;

    private LocalDate dateOfBirth;

    private String address;

    // Not relying on createdAt as it is for auditing purpose, and it refers to when record will be inserted to DB.
    private LocalDate registeredDate;

    @PrePersist
    public void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }

}
