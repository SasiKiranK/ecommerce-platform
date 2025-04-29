# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21 as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy jar
COPY --from=builder /app/target/product-service-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Enable Spring Boot Devtools
ENV SPRING_DEVTOOLS_RESTART_ENABLED=true

# Run the app
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]
