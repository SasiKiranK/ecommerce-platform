FROM eclipse-temurin:21-jdk
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy full source code
COPY . .

# Expose port
EXPOSE 8080

# Enable DevTools
ENV SPRING_DEVTOOLS_RESTART_ENABLED=true

# Start using mvn spring-boot:run (which compiles code on-the-fly)
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.fork=false"]
