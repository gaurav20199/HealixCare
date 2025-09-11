package com.project.patientmanagementservice.utils;

import com.project.patientmanagementservice.dto.PatientRequestDTO;
import com.project.patientmanagementservice.dto.PatientResponseDTO;
import com.project.patientmanagementservice.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PatientDTOMapper {

    //@Mapping(target = "uuid", expression = "java(patient.getUuid() != null ? patient.getUuid().toString() : null)")
    @Mapping(target = "uuid", qualifiedByName = "getUuidForConversion")
    @Mapping(target = "dateOfBirth", qualifiedByName = "dateToStringOrEmpty")
    PatientResponseDTO toDto(Patient patient);


    Patient toEntity(PatientRequestDTO patientRequestDTO);


    @Named("getUuidForConversion")
    default String getUuidForConversion(UUID uuid) {
        return uuid != null ? uuid.toString() : "";
    }

    @Named("dateToStringOrEmpty")
    default String dateToStringOrEmpty(LocalDate date) {
        return date != null ? date.toString() : "";
    }


}
