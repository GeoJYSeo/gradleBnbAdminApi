# Use the official maven/Java 11 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3-jdk-11-slim AS build-env

# Set the working directory to /app
WORKDIR /api
# Copy the pom.xml file to download dependencies
#COPY pom.xml ./
# Copy local code to the container image.
COPY . /api/

# Download dependencies and build a release artifact.
RUN chmod +x gradlew
RUN ./gradlew bootRun

# Use OpenJDK for base image.
# https://hub.docker.com/_/openjdk
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
#FROM openjdk:11-jre-slim
#
## Copy the jar to the production image from the builder stage.
#COPY --from=build-env /api/build/libs/*.jar /gradle-bnb-admin-api.jar
#
## Run the web com.example.gradlebnbadminapi.service on container startup.
#CMD ["java", "-jar", "/gradle-bnb-admin-api.jar"]