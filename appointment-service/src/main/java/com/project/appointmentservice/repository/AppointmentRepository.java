package com.project.appointmentservice.repository;

import com.project.appointmentservice.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByStartTimeBetween(LocalDate from, LocalDate to);
}
