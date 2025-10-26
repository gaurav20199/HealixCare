package com.project.appointmentservice.controller;

import com.project.appointmentservice.dto.AppointmentResponseDTO;
import com.project.appointmentservice.service.AppointmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping({"/appointments","/appointments/"})
public class AppointmentController {

  private final AppointmentService appointmentService;

  public AppointmentController(AppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  @GetMapping
  public List<AppointmentResponseDTO> getAppointmentsByDateRange(@RequestParam LocalDateTime from,
                                                                 @RequestParam LocalDateTime to){
    return appointmentService.getAppointmentsByDateRange(from, to);
  }
}