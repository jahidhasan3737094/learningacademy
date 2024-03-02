# Use AdoptOpenJDK 17 as base image
FROM openjdk:17

# Set working directory
#WORKDIR /app

# Copy Maven build files
COPY pom.xml .
COPY src ./src

# Build the project
#RUN ./mvnw package -DskipTests

# Create a smaller JRE image for production
FROM openjdk:17-jdk-slim

# Set working directory
#WORKDIR /app

# Copy the built JAR file from the builder stage
COPY /target/test-classes/learningacademy-0.0.1-SNAPSHOT.jar learningacademy.jar

# Expose the port
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "learningacademy.jar"]
