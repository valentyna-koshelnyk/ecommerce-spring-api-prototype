# Use OpenJDK 17 JDK for the build stage
FROM --platform=$BUILDPLATFORM openjdk:17-jdk-slim as builder

# Set work directory
WORKDIR /app

# Install Maven and dos2unix
RUN apt-get update && \
    apt-get install -y maven
RUN apt-get update && apt-get install -y dos2unix

# Copy your project's POM.xml and source files
COPY pom.xml .
COPY mvnw .
RUN dos2unix mvnw && chmod +x mvnw
# Use this optimization to cache the local Maven dependencies. Works as long as the POM doesn't change
COPY src/ /app/src/
RUN mvn package
# Package your application

ENV PORT=8080

# Use the same OpenJDK image for the runtime stage
FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=builder /app/target/*.jar /app.jar

# Run the web service on container startup with JDWP enabled for debugging
ENTRYPOINT ["java","-Djava.security.egd=file:/prod/./urandom", "-jar", "/app.jar"]
