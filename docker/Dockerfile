# Используем созданный ранее command-line-runner-docker-0.0.1.jar, поэтому в FROM можно указать JRE, вместо JDK
FROM openjdk:17-jdk-alpine as openJdk17_from
#FROM openjdk:11-ea-17-jre-slim as openJre17_from
LABEL authors="Artem:"
MAINTAINER Капоров Артём <voropak@yandex.ru>
ARG JAR_FILE=target/*.jar
WORKDIR /app
#COPY target/command-line-runner-docker-0.0.1.jar /app/command-line-runner-docker-0.0.1.jar
COPY ${JAR_FILE} /command-line-runner-docker.jar
EXPOSE 8080
#ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/command-line-runner-docker.jar"]
ENTRYPOINT ["java", "-jar", "/command-line-runner-docker.jar"]