# Use a maven image to build the Java project
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app

# Copy the source code and pom.xml
COPY src ./src
COPY pom.xml ./

# Build the JAR file
RUN mvn clean install

# Package only the built JAR file
FROM alpine:3.12 AS output
WORKDIR /output
COPY --from=build /app/target/basic-upload-resources-1.0-SNAPSHOT.jar ./basic-upload-resources-1.0-SNAPSHOT.jar
