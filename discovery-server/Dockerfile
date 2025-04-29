# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21 as builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package spring-boot:repackage -DskipTests

# Stage 2: Run
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /build/target/discovery.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
