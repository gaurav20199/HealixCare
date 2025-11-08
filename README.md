# 🏥 HealixCare

**HealixCare** is a modern, microservices-based healthcare management platform designed to streamline patient care, appointments, billing, and analytics.  
It leverages a scalable architecture with real-time communication, caching, and observability baked in.

## ⚙️ Tech Stack

Java 21+ / Spring Boot – Core microservices built using Spring Boot for rapid development and modular services.

Spring Cloud / API Gateway – Requests are routed and managed via a gateway layer. Each request is Authenticated. Rate Limiting has been added as well.

gRPC & REST – Inter-service communication via gRPC (for internal high-performance flows) and REST APIs (for external clients).

Docker – Each microservice and infrastructure component is containerised for consistent deployment.

Asynchronous & Event-Driven Architecture – Core message bus for decoupled inter-service communication.Used for events like patient registered, appointment created and other events.

Resiliancy – Automatically opens circuits to stop cascading failures. Support for fallback and retries has been added for production grade fault tolerance.

Monitoring & Observability – Included monitoring stack with metrics, logs and traces. Combinaes Spring Actuator with prometheus and Grafana to build dashboards.

Documentation – Leveraged Swagger to deliver unified API documentation in both OpenAPI (JSON) and interactive web UI formats.

Infrastructure as Code – Infrastructure definitions and service deployment patterns in a dedicated folder.

MySQL / PostgreSQL / NoSQL – Depending on service domain (e.g., billing, analytics) uses appropriate data stores.

JWT – Authentication and authorisation handled via the Auth-service using JWT tokens.

CI/CD ready – Project structured for integration into automated pipelines (GitHub Actions / Jenkins) and container registry.


🧩 Key Features

Multi-tenant microservices: patient management, billing, appointment scheduling and analytics.

Centralised authentication with role-based access control.

Real-time notifications for Patient, Appointment and other events.

Observability built-in: metrics, logs and traces across services.

Extensible architecture: easy to add new microservices.




