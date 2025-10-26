package com.project.appointmentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "cached_patient")
public class CachedPatient extends BaseEntity {
    private UUID id;
    private String fullName;
    private String email;
}
