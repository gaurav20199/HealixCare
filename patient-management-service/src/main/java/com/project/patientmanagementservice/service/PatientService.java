package com.project.patientmanagementservice.service;

import com.project.patientmanagementservice.dto.PageResult;
import com.project.patientmanagementservice.dto.PatientRequestDTO;
import com.project.patientmanagementservice.dto.PatientResponseDTO;
import com.project.patientmanagementservice.entity.Patient;
import com.project.patientmanagementservice.exception.InvalidEmailException;
import com.project.patientmanagementservice.exception.PatientNotFoundException;
import com.project.patientmanagementservice.grpc.BillingServiceGrpcClient;
import com.project.patientmanagementservice.kafka.PatientEventProducer;
import com.project.patientmanagementservice.repository.PatientRepository;
import com.project.patientmanagementservice.utils.PatientDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientDTOMapper patientDTOMapper;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final PatientEventProducer patientEventProducer;

    private Pageable getPageableObj(int pageNumber, int pageSize, String sortDirection) {
        pageNumber = pageNumber>1?pageNumber-1:0;
        return PageRequest.of(pageNumber,pageSize, Sort.by(sortDirection.equalsIgnoreCase("DESC")?
                Sort.Direction.DESC:Sort.Direction.ASC, "id"));
    }

    public PageResult<PatientResponseDTO> getAllPatients(int page, int size, String sortDirection) {
        Pageable pageableObj = getPageableObj(page, size, sortDirection);
        Page<PatientResponseDTO> patients = patientRepository.findAll(pageableObj).map(patientDTOMapper::toDto);
        return PageResult.from(patients);
    }

    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail()))
            throw new InvalidEmailException("Invalid email id provided");

        Patient patient = patientDTOMapper.toEntity(patientRequestDTO);
        patientRepository.save(patient);

        billingServiceGrpcClient.createBillingAccount(patient.getUuid().toString(), patient.getName(), patient.getEmail());

        patientEventProducer.sendEvent(patient);

        return patientDTOMapper.toDto(patient);
    }

    @Transactional
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

    @Transactional
    public void deletePatient(UUID uuid) {
        patientRepository.deleteByUuid(uuid);
    }

}
