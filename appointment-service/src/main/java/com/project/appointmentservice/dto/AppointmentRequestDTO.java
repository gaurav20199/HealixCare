package com.project.appointmentservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequestDTO(UUID patientId, LocalDateTime startDateTime, LocalDateTime endDateTime,String reason) {
}
