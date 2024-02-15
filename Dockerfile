FROM eclipse-temurin:17-jdk-jammy

LABEL maintainer="vakosheln@example.com"

VOLUME /tmp
WORKDIR /app
COPY .mvn/ ./mvn

EXPOSE 8080
COPY mvnw pom.xml ./
COPY src ./src

ARG JAR_FILE=target/ecommerce-api-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar


CMD ["./mvnw", "spring-boot:run"]

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
