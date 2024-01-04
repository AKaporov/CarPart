# Собрка на openjdk:17-jdk-alpine за один раз = 377MB
# Собрка на eclipse-temurin:17-jre-alpine за один раз = 228MB
# Используем созданный ранее command-line-runner-docker-*.jar (проверить, что в .dockerignore не указана /targer), поэтому в FROM можно указать JRE, вместо JDK
#FROM openjdk:17-jdk-alpine
FROM eclipse-temurin:17-jre-alpine
LABEL authors="Artem:"
MAINTAINER Капоров Артём <voropak@yandex.ru>
#создаем просто локальную переменную, которая ссылается на jar-ник нашего проекта.
ARG JAR_FILE=target/*.jar
#WORKDIR /app
#копируем существующий jar-ник в образ и называем его command-line-runner-docker.jar.
COPY ${JAR_FILE} ./command-line-runner-docker.jar
# указываем на каком внешнем порту будет доступен наш контейнер.
EXPOSE 8080
# пример варианта запуска jar с профилем dockerfile (сейчас профиль указан в docker-compose в SPRING_PROFILES_ACTIVE, тут переопределять НЕ НУЖНО!!!!)
#ENTRYPOINT ["java", "-Dspring.profiles.active=dockerfile", "-jar", "/command-line-runner-docker.jar"]
ENTRYPOINT ["java", "-jar", "/command-line-runner-docker.jar"]
#ENTRYPOINT java -jar /command-line-runner-docker.jar






# Многоступенчатая сборка = 156MB
#(Для уменьшения размера лучше использовать образы на основе Alpine – образы на основе данного дистрибутива самые легковесные.
#Компилируем при помощи образа jdk, запускаем с помощью jre (бережем место на диске, оно нам еще понадобится))

## Указываем именно jdk, так как нам понадобятся инструменты для компиляции нашего проекта
## первый этап сборки
#FROM openjdk:17-alpine3.14 AS openJdk17_from
#LABEL authors="Artem:"
#
#ENV HOME=/tmp/parentdocker
#RUN mkdir -p $HOME  #Задавая следующий переключатель вы можете создать все родительские папки на лету, если они уже не существуют.
#WORKDIR $HOME
#
## копируем maven
#COPY ./docker/.mvn $HOME/.mvn
#COPY ./docker/mvnw $HOME
#
#ADD ./pom-for-docker.xml $HOME/pom.xml
#ADD ./docker/pom.xml $HOME/docker/pom.xml
#
#RUN $HOME/mvnw -pl docker clean verify --fail-never
#ADD ./docker $HOME/docker
#RUN $HOME/mvnw -pl docker clean install -DskipTests
#RUN $HOME/mvnw -pl docker clean package -DskipTests
#
#
## второй этап сборки
#FROM openjdk:8-jdk-alpine AS second
##FROM openjdk:11-jre-slim AS second
#COPY --from=openJdk17_from /tmp/parentdocker/docker/target/*.jar /command-line-runner-docker.jar
## указываем на каком внешнем порту будет доступен наш контейнер.
#EXPOSE 8080
## пример варианта запуска jar с профилем dockerfile (сейчас профиль указан в docker-compose в SPRING_PROFILES_ACTIVE, тут переопределять НЕ НУЖНО!!!!)
##ENTRYPOINT ["java", "-Dspring.profiles.active=dockerfile", "-jar", "/command-line-runner-docker.jar"]
#ENTRYPOINT ["java", "-jar", "/command-line-runner-docker.jar"]
##ENTRYPOINT java -jar /command-line-runner-docker.jar








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
