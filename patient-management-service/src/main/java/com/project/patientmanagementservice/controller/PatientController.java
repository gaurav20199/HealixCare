package com.project.patientmanagementservice.controller;

import com.project.patientmanagementservice.dto.PatientRequestDTO;
import com.project.patientmanagementservice.dto.PatientResponseDTO;
import com.project.patientmanagementservice.dto.validator.CreatePatientValidationGroup;
import com.project.patientmanagementservice.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        List<PatientResponseDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return new ResponseEntity<>(patientResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID uuid,
                                                            @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {

        PatientResponseDTO patientResponseDTO = patientService.updatePatient(uuid, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID uuid) {
        patientService.deletePatient(uuid);
        return ResponseEntity.noContent().build();
    }

}
