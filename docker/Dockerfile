# Используем созданный ранее command-line-runner-docker-0.0.1.jar, поэтому в FROM можно указать JRE, вместо JDK
FROM openjdk:17-jdk-alpine as openJdk17_from
#FROM openjdk:11-ea-17-jre-slim as openJre17_from
ARG TITLE
ARG VERSION
ARG ID
ARG DATE
LABEL authors="Artem:" title=${TITLE} version=${VERSION} id=${ID} date=${DATE}
MAINTAINER Капоров Артём <voropak@yandex.ru>
#LABEL authors="Artem"

WORKDIR /app
COPY target/command-line-runner-docker-0.0.1.jar /app/command-line-runner-docker-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "command-line-runner-docker-0.0.1.jar"]