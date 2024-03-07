# Собрка на openjdk:17-jdk-alpine за один раз = 377MB
# Собрка на eclipse-temurin:17-jre-alpine за один раз = 228MB

# !!! Используем созданный ранее command-line-runner-docker-*.jar (проверить, что в .dockerignore не указана /targer), поэтому в FROM можно указать JRE, вместо JDK !!!

##FROM openjdk:17-jdk-alpine
#FROM eclipse-temurin:17-jre-alpine
#LABEL authors="Artem:"
#MAINTAINER Капоров Артём <voropak@yandex.ru>
##создаем просто локальную переменную, которая ссылается на jar-ник нашего проекта.
#ARG JAR_FILE=target/*.jar
##копируем существующий jar-ник в образ и называем его command-line-runner-docker.jar.
#COPY ${JAR_FILE} ./command-line-runner-docker.jar
## указываем на каком внешнем порту будет доступен наш контейнер.
#EXPOSE 8080
## пример варианта запуска jar с профилем dockerfile (сейчас профиль указан в docker-compose в SPRING_PROFILES_ACTIVE, тут переопределять НЕ НУЖНО!!!!)
##ENTRYPOINT ["java", "-Dspring.profiles.active=dockerfile", "-jar", "/command-line-runner-docker.jar"]
#ENTRYPOINT ["java", "-jar", "/command-line-runner-docker.jar"]
##ENTRYPOINT java -jar /command-line-runner-docker.jar



# ================================= Multi-Stage =================================


# Многоступенчатая сборка
# (openjdk:17-alpine3.14 + openjdk:17-jdk-alpine) = 377MB
# (eclipse-temurin:17-jdk-alpine + eclipse-temurin:17-jre-alpine) = 228MB

#(Для уменьшения размера лучше использовать образы на основе Alpine – образы на основе данного дистрибутива самые легковесные.
#Компилируем при помощи образа jdk, запускаем с помощью jre (бережем место на диске, оно нам еще понадобится))

# !!! Запускать сборку из РОДИТЕЛЬСКОГО каталога т.е. из E:\Education\Programming\Java\CarPart> docker build -f (см. ReadMe.md (пункт 1 раздела "Пример создания Images из Terminal для Multi-Stage-Module")

## Указываем именно jdk, так как нам понадобятся инструменты для компиляции нашего проекта
## первый этап сборки
#FROM openjdk:17-alpine3.14 AS openJdk17_from
#
#LABEL authors="Artem:"
#MAINTAINER Капоров Артём <voropak@yandex.ru>
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
## собираем указанный модуль т.е. docker
#RUN $HOME/mvnw -pl docker clean verify --fail-never
#ADD ./docker $HOME/docker
#RUN $HOME/mvnw -pl docker clean install -DskipTests
#RUN $HOME/mvnw -pl docker clean package -DskipTests

# !!! Запускать сборку из каталога, где находится Dockerfile т.е. из E:\Education\Programming\Java\CarPart\docker> docker build (см. ReadMe.md (пункт 2 раздела "Пример создания Images из Terminal для Multi-Stage-Module")
# Указываем именно jdk, так как нам понадобятся инструменты для компиляции нашего проекта
# первый этап сборки
#FROM openjdk:17-alpine3.14 AS openJdk17_from
FROM eclipse-temurin:17-jdk-alpine AS openJdk17_from

LABEL authors="Artem:"
MAINTAINER Капоров Артём <voropak@yandex.ru>

ENV HOME=/tmp
#RUN mkdir -p $HOME  #Задавая следующий переключатель вы можете создать все родительские папки на лету, если они уже не существуют.
WORKDIR $HOME

# копируем maven
COPY ./.mvn $HOME/docker/.mvn
COPY ./mvnw $HOME/docker

COPY ./pom-for-docker.xml $HOME/pom.xml
COPY ./pom.xml $HOME/docker/pom.xml
RUN $HOME/docker/mvnw dependency:go-offline
COPY ./src $HOME/docker/src
# собираем указанный модуль т.е. docker
RUN $HOME/docker/mvnw -pl docker clean install -DskipTests


# второй этап сборки
#FROM openjdk:17-jdk-alpine AS second
FROM eclipse-temurin:17-jre-alpine AS second

COPY --from=openJdk17_from /tmp/docker/target/*.jar /command-line-runner-docker.jar
# указываем на каком внешнем порту будет доступен наш контейнер.
EXPOSE 8080
# пример варианта запуска jar с профилем dockerfile (сейчас профиль указан в docker-compose в SPRING_PROFILES_ACTIVE, тут переопределять НЕ НУЖНО!!!!)
#ENTRYPOINT ["java", "-Dspring.profiles.active=dockerfile", "-jar", "/command-line-runner-docker.jar"]
ENTRYPOINT ["java", "-jar", "/command-line-runner-docker.jar"]
#ENTRYPOINT java -jar /command-line-runner-docker.jar

