package com.project.patientmanagementservice.aspects;

import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PatientServiceMetrics {

  private static final Logger log = LoggerFactory.getLogger(PatientServiceMetrics.class);
  private final MeterRegistry meterRegistry;

  public PatientServiceMetrics(MeterRegistry meterRegistry){
    this.meterRegistry = meterRegistry;
  }

  @Around("execution(* com.project.patientmanagementservice.service.PatientService.getAllPatients(..))")
  public Object monitorGetPatients(ProceedingJoinPoint joinPoint) throws Throwable {
    log.debug("Entered Aspect Execution for Get All Patients");
    log.info("[Redis]: Cache miss. Records will be fetched from DB");
    meterRegistry.counter("custom.service.redis.cache.miss", "cache", "patients")
        .increment();
    return joinPoint.proceed();
  }
}