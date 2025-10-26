package com.project.appointmentservice.repository;

import com.project.appointmentservice.entity.CachedPatient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CachedPatientRepository extends JpaRepository<CachedPatient, Integer> {
    Optional<CachedPatient> findByUuid(UUID id);
}
