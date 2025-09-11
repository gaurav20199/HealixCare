package com.project.patientmanagementservice.service;

import com.project.patientmanagementservice.dto.PatientRequestDTO;
import com.project.patientmanagementservice.dto.PatientResponseDTO;
import com.project.patientmanagementservice.entity.Patient;
import com.project.patientmanagementservice.exception.InvalidEmailException;
import com.project.patientmanagementservice.exception.PatientNotFoundException;
import com.project.patientmanagementservice.repository.PatientRepository;
import com.project.patientmanagementservice.utils.PatientDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientDTOMapper patientDTOMapper;

    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(patientDTOMapper::toDto).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail()))
            throw new InvalidEmailException("Invalid email id provided");

        Patient patient = patientDTOMapper.toEntity(patientRequestDTO);
        patientRepository.save(patient);
        return patientDTOMapper.toDto(patient);
    }

    public PatientResponseDTO updatePatient(UUID uuid,
                                            PatientRequestDTO patientRequestDTO) {

        Patient patient = patientRepository.findByUuid(uuid).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID: " + uuid));

        if (patientRepository.existsByEmailAndUuidNot(patientRequestDTO.getEmail(),uuid)) {
            throw new InvalidEmailException(
                    "A patient with this email " + "already exists"
                            + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return patientDTOMapper.toDto(updatedPatient);
    }

}
