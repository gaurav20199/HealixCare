package com.project.appointmentservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import com.project.appointmentservice.entity.CachedPatient;
import com.project.appointmentservice.repository.CachedPatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

import java.util.UUID;

@Service
public class KafkaConsumer {

  private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
  private final CachedPatientRepository cachedPatientRepository;

  public KafkaConsumer(CachedPatientRepository cachedPatientRepository) {
    this.cachedPatientRepository = cachedPatientRepository;
  }


  @KafkaListener(
      topics = {"patient"},
      groupId = "appointment-service")
  public void consumeEvent(byte[] event) {
    try {
      PatientEvent patientEvent = PatientEvent.parseFrom(event);

      log.info("Received Patient {} Event: {}",patientEvent.getEventType(), patientEvent);
      CachedPatient cachedPatient = patientEvent.getEventType().equals("PATIENT_UPDATED")?cachedPatientRepository.findByUuid(UUID.fromString(patientEvent.getPatientId())).orElseThrow(() -> new RuntimeException("Patient not found")):
              new CachedPatient();

      cachedPatient.setUuid(UUID.fromString(patientEvent.getPatientId()));
      cachedPatient.setFullName(patientEvent.getName());
      cachedPatient.setEmail(patientEvent.getEmail());
      cachedPatientRepository.save(cachedPatient);

    } catch (InvalidProtocolBufferException e) {
      log.error("Error deserializing Patient Event: {}", e.getMessage());
    } catch (Exception e) {
      log.error("Error consuming Patient Event: {}", e.getMessage());
    }
  }
}