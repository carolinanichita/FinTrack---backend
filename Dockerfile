# Stage 1: Build JAR
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Stage 2: Create Final Image
FROM openjdk:17

WORKDIR /app
COPY --from=build /app/target/FinTrackBackend-0.0.1-SNAPSHOT.jar FinTrackBackend.jar

# Specify the command to run on container start
CMD ["java", "-jar", "FinTrackBackend.jar"]
