package com.project.patientmanagementservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import com.project.patientmanagementservice.kafka.KafkaEventProducer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {

    private KafkaEventProducer kafkaEventProducer;
    private static final Logger log = LoggerFactory.getLogger(
            BillingServiceGrpcClient.class);

    private final BillingServiceGrpc.BillingServiceBlockingStub stub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort,
            KafkaEventProducer kafkaEventProducer) {

        log.info("Connecting to Billing Service GRPC service at {}:{}",
                serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,
                serverPort).usePlaintext().build();

        stub = BillingServiceGrpc.newBlockingStub(channel);
        this.kafkaEventProducer = kafkaEventProducer;
    }

    // name provided here will be used in application properties file to define circuit breaker configuration
    // corresponding to a particular service
    @CircuitBreaker(name="billingService",fallbackMethod = "billingFallback")
    @Retry(name="billingRetry")
    public BillingResponse createBillingAccount(String patientId, String name,
                                                String email) {

        BillingRequest request = BillingRequest.newBuilder().setPatientId(patientId)
                .setName(name).setEmail(email).build();

        BillingResponse response = stub.createBillingAccount(request);
        log.info("Received response from billing service via GRPC: {}", response);
        return response;
    }

    public BillingResponse billingFallback(String patientId, String name, String email, Throwable throwable) {
        log.warn("[Circuit Breaker]. Billing Service is not available. Falling back to billing service via GRPC");
        kafkaEventProducer.sendBillingAcount(patientId,name, email);
        return BillingResponse.newBuilder().setAccountId("").setStatus("PENDING").build();
    }


}
