#Use AdoptOpenJDK 17 as base image
FROM adoptopenjdk:17-jdk-hotspot as builder
#set working directory
WORKDIR /app

#copy Maven build files
COPY pom.xml .
COPY src ./src

#build the project
RUN ./mvnw package -DskipTests

#Create a similer JRE image for production
FROM adoptopenjdk:17-jre-hotspot
# Set working directory
WORKDIR /app
# Copy the built JAR file from the builder stage
COPY --from=builder   target/learningacademy-0.0.1-SNAPSHOT.jar app.jar
# Expose the port
EXPOSE 8080
# Command to run the application
CMD ["java", "-jar", "app.jar"]
#ENTRYPOINT ["java","-jar","/app.jar"]
