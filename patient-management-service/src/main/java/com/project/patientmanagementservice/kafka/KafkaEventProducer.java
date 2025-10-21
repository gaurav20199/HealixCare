package com.project.patientmanagementservice.kafka;

import billing.events.BillingAccountEvent;
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
public class KafkaEventProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventProducer.class);
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

    public void sendBillingAcount(String patientId, String name, String email) {
        BillingAccountEvent billingAccountEvent = BillingAccountEvent.newBuilder().
                                                    setEmail(email).
                                                    setName(name).
                                                    setEventType(EventType.BILLING_ACCOUNT_CREATION_REQUESTED.toString()).
                                                    setPatientId(patientId).
                                                    build();

        try {
            kafkaTemplate.send("billing-account",billingAccountEvent.toByteArray());
        }catch (Exception e){
            log.error("Error sending event",e);
            log.error("Error occurred for event",billingAccountEvent);
        }
    }
}
