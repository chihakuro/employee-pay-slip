# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-slim

# Set the working directory
WORKDIR /app

# Copy the generated JAR file
COPY build/libs/EmployeePaySlip-1.0.0.jar /app/EmployeePaySlip-1.0.0.jar

# Expose the application's port (default Spring Boot port)
EXPOSE 80

# Run the JAR file
ENTRYPOINT ["java", "-jar", "EmployeePaySlip-1.0.0.jar"]
