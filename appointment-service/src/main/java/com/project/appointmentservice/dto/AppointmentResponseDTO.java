package com.project.appointmentservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponseDTO(UUID patientId, String patientName, LocalDateTime startDateTime, LocalDateTime endDateTime, String reason) {
}
