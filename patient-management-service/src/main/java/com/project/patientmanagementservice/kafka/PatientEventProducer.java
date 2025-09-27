package com.project.patientmanagementservice.kafka;

import com.project.patientmanagementservice.entity.Patient;
import com.project.patientmanagementservice.enums.EventType;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@AllArgsConstructor
@Service
public class PatientEventProducer {

    private static final Logger log = LoggerFactory.getLogger(PatientEventProducer.class);
    private final KafkaTemplate<String,byte[]> kafkaTemplate;

    public void sendEvent(Patient patient){
        PatientEvent patientEvent = PatientEvent.newBuilder().
                                                setPatientId(patient.getUuid().toString()).
                                                setEmail(patient.getEmail()).
                                                setName(patient.getName()).
                                                setEventType(EventType.PATIENT_CREATED.toString()).build();
        try {
            kafkaTemplate.send("patient",patientEvent.toByteArray());
        }catch (Exception e){
            log.error("Error sending event",e);
            log.error("Error occurred for event",patientEvent);
        }
    }
}
