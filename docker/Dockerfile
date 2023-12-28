## Собрка за один раз = 377MB
## Используем созданный ранее command-line-runner-docker-0.0.1.jar, поэтому в FROM можно указать JRE, вместо JDK
#FROM openjdk:17-jdk-alpine AS openJdk17_from
#LABEL authors="Artem:"
#MAINTAINER Капоров Артём <voropak@yandex.ru>
#ARG JAR_FILE=target/*.jar
#WORKDIR /app
##COPY target/command-line-runner-docker-0.0.1.jar /app/command-line-runner-docker-0.0.1.jar
#COPY ${JAR_FILE} /command-line-runner-docker.jar
#EXPOSE 8080
## вариант запуска с профилем docker
##ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/command-line-runner-docker.jar"]
#ENTRYPOINT ["java", "-jar", "/command-line-runner-docker.jar"]






# Многоступенчатая сборка = 156MB
#(Для уменьшения размера лучше использовать образы на основе Alpine – образы на основе данного дистрибутива самые легковесные.
#Компилируем при помощи образа jdk, запускаем с помощью jre (бережем место на диске, оно нам еще понадобится))

# Указываем именно jdk, так как нам понадобятся инструменты для компиляции нашего проекта
# first stage
FROM openjdk:17-alpine3.14 AS openJdk17_from
LABEL authors="Artem:"

ENV HOME=/tmp/parentdocker
RUN mkdir -p $HOME  #Задавая следующий переключатель вы можете создать все родительские папки на лету, если они уже не существуют.
WORKDIR $HOME

# копируем maven
COPY ./docker/.mvn $HOME/.mvn
COPY ./docker/mvnw $HOME

ADD ./pom-for-docker.xml $HOME/pom.xml
ADD ./docker/pom.xml $HOME/docker/pom.xml

RUN $HOME/mvnw -pl docker clean verify --fail-never
ADD ./docker $HOME/docker
RUN $HOME/mvnw -pl docker clean install -DskipTests
RUN $HOME/mvnw -pl docker clean package -DskipTests


# second stage
FROM openjdk:8-jdk-alpine AS second
#FROM openjdk:11-jre-slim AS second
COPY --from=openJdk17_from /tmp/parentdocker/docker/target/*.jar /app/docker.jar
ENTRYPOINT java -jar /app/docker.jar
#CMD ["java", "-jar", "/app/docker.jar"]







#RUN /opt/app/mvnw -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline
#RUN ./mvnw dependency:go-offline -B
#RUN ./mvnw -f ./pom.xml clean install
# Use your secret settings.xml
#RUN --mount=type=secret,id=mvnsettings,target=/root/.m2/settings.xml \
#    ./mvnw dependency:go-offline -B \
#RUN ./mvnw dependency:go-offline -B


#LABEL authors="Artem:"
#MAINTAINER Капоров Артём <voropak@yandex.ru>
#ARG JAR_FILE=target/*.jar
#WORKDIR /app
#COPY ${JAR_FILE} /command-line-runner-docker.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/command-line-runner-docker.jar"]
