package com.project.patientmanagementservice.repository;

import com.project.patientmanagementservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndUuidNot(String email, UUID uuid);

    Optional<Patient> findByUuid(UUID uuid);
}
