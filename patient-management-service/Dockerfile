FROM eclipse-temurin:24-jdk as builder

WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:24-jdk AS runner

WORKDIR /app

COPY --from=builder ./app/target/patient-management-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]