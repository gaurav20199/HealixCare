package com.project.appointmentservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "cached_patient")
@Getter
@Setter
public class CachedPatient extends BaseEntity {
    @Column(name = "patient_id")
    private UUID uuid;
    private String fullName;
    private String email;
}
