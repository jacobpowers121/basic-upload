# Stage 1: Build basic-upload-resources
FROM maven:3.8.1-openjdk-17 AS resources-build
WORKDIR /resources

# Copy the basic-upload-resources source code
COPY basic-upload-resources/pom.xml ./
COPY basic-upload-resources/src ./src

# Build and install basic-upload-resources JAR into local Maven repository
RUN mvn clean install

# Stage 2: Build backend service
FROM maven:3.8.1-openjdk-17 AS backend-build
WORKDIR /app

# Copy the backend service source code
COPY pom.xml ./
COPY src ./src

# Copy the basic-upload-resources JAR from the previous stage into the local Maven repository
COPY --from=resources-build /root/.m2 /root/.m2

# Build the backend service
RUN mvn clean package

# Stage 3: Create the runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=backend-build /app/target/*.jar ./basic-upload-service.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "basic-upload-service.jar"]
