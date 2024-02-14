FROM openjdk:17-jdk-alpine

LABEL maintainer="vakosheln@example.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/ecommerce-api-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
