package com.project.appointmentservice.service;

import com.project.appointmentservice.entity.CachedPatient;
import com.project.appointmentservice.repository.AppointmentRepository;
import com.project.appointmentservice.repository.CachedPatientRepository;
import com.project.appointmentservice.dto.AppointmentResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private static final Logger log = LoggerFactory.getLogger(AppointmentService.class);
    private final AppointmentRepository appointmentRepository;
  private final CachedPatientRepository cachedPatientRepository;

  public AppointmentService(AppointmentRepository appointmentRepository,
      CachedPatientRepository cachedPatientRepository) {
    this.appointmentRepository = appointmentRepository;
    this.cachedPatientRepository = cachedPatientRepository;
  }

  public List<AppointmentResponseDTO> getAppointmentsByDateRange(LocalDateTime from, LocalDateTime to) {
    return appointmentRepository.findByStartTimeBetween(from, to).stream()
        .map(appointment -> {

          String patientName = cachedPatientRepository
              .findByUuid(appointment.getPatientId())
              .map(CachedPatient::getFullName)
              .orElse("Unknown");

          return new AppointmentResponseDTO(appointment.getPatientId(), patientName, appointment.getStartTime(),
                  appointment.getEndTime(), appointment.getReason());
        }).toList();
  }
}
