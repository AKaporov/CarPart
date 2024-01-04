# В проекте (на основе модуля Command-Line-Runner) применяется Docker

В проекте создается Image приложения. Тестирование производиться на базе PostgreSQl из контейнера, так же подключен
контейнер [PgAdmin](http://localhost:5050/browser/) для просмотра базы данных после запуска приложения.

**Перед запуском приложения/тестов запустить Docker Desktop**

***При одноступечатой сборки создание образа и запуск контейнера(docker-compose = command-line-runner-docker-container) производить из файла docker-compose.yml***

## **Пример порядка запуска контейнеров (docker-compose.yml) на локальном компьютере**
1) Запуск docker-compose из корня модуля docker (С:\Java\CarPart\docker> docker-compose up -d)
```
doker-compose up -d
```
2) Проверяем создание контейнеров
```
docker-compose ps
```
![alt-текст][logo_docker_compose_ps]

3) Для просмотра логов используем команду
```
docker-compose logs -f <имя SERVICE. Например PostgreSQL-Container>
```
4) Пример настройки подключения к БД:

![alt-текст][logo_PgAdmin_DB_Connection]

![alt-текст][logo_IDEA_DB_Connection]

5) **В браузере или postman можно выполнить REST-ы указанные в разделе "Команды для запуска в браузере (после запуска приложения)" из основного ReadMe.md**

### **docker/src/main/resources/application.yml** 
* для запуска приложения в IDEA или в командной строке.
### **docker/src/main/resources/application-docker-cmp.yml** 
* для запуска приложения в контейнере. В **spring.datasource.url:** указано имя контейнера, что бы приложение в Docker-е смогло 
достучаться к БД. Потому что они запустятся в рамках одного docker-compose и будут знать друг о друге. Это нужно, чтобы
у нас была одна закрытая инфраструктура для проекта.

## Пример создания Images из Terminal для Multi-Stage-Module
Запуск создания образа для multi-module сборки из родительского каталога нужно производить с флагом **-f**
(PS E:\Education\Programming\Java\CarPart> docker build -f .\docker\Dockerfile -t car_part:3 .)
```Пример
docker build -f .\docker\Dockerfile -t car_part:3 .
```
![alt-текст][logo_docker_build]
* [error "failed to compute cache key: not found"](https://stackoverflow.com/questions/66146088/docker-gets-error-failed-to-compute-cache-key-not-found-runs-fine-in-visual)

### Docker Compose
* это инструмент, который упрощает управление многоконтейнерными приложениями с использованием Docker. Он позволяет 
вам определить и настроить все контейнеры, сети и объемы для вашего приложения в одном файле конфигурации в формате 
YAML, а затем запустить их одной командой.

### **Dockerfile** 
* это текстовый файл, который содержит инструкции для создания Docker-образа. Образ Docker - это пакет, который 
включает в себя все необходимое для запуска контейнера, включая код, зависимости, переменные окружения и многое другое.

**Container** - текущий экземпляр, который инкапсулирует необходимое ПО. Контейнеры всегда создаются из **Image**. 
Могут открывать **Port**-ы и **Volume**-ы для взаимодействия с другими контейнерами и/или внешним миром, а также легко 
удаляются и пересоздаются заново за короткий промежуток времени.

**Image** - основной элемент для каждого **Container**. После его создания каждый шаг кэшируется и может быть 
использован повторно (модель копирования при записи). В зависимости от **Image** может понадобиться некоторое время 
для его построения. Из них могут быть сразу запущены контейнеры.

**Port** - TCP/UDP-порт в своем обычном представлении. Порты могут быть открыты для внешнего мира (доступы через 
основную ОС) или подсоединены к другим контейнерам — доступны только из тех контейнеров и невидимы для внешнего мира.

**Volume** - может быть описан как общая папка. **Volume**(тома) инициализируются при создании контейнера. 
Они спроектированы для хранения данных независимо от жизненного цикла контейнера.

**Registry** - сервер, который хранит образы Docker. Функционирует по аналогии с Github — можно вытянуть образ, чтобы 
развернуть его локально, а затем закинуть обратно в реестр. 

**[Docker Hub](https://hub.docker.com/search?q=&type=image)** - реестр с веб-интерфейсом, созданный Docker Inc. 
Он хранит большое количество Docker-образов с разным ПО. Docker Hub является источником «официальных» Docker-образов, 
созданных командой Docker. Официальные образы содержат списки своих потенциальных уязвимостей. Эта информация доступна 
для каждого авторизированного пользователя. Доступны два типа аккаунтов: бесплатные и платные. На бесплатном аккаунте 
может быть один приватный образ и бесконечное количество общедоступных образов.

## Команды Docker
### Основные команды:
1) Команда **docker pull** используется для загрузки образа с Docker Hub:
```Например:
# Эта команда загрузит образ Ubuntu версии 20.04.
docker pull ubuntu:20.04
```
2) Команда **docker run** используется для запуска контейнера из образа:
```Например:
# -it: Флаги -it обеспечивают интерактивный режим и привязку к терминалу контейнера.
docker run -it ubuntu:20.04 /bin/bash
```
3) Команда **docker ps** выводит список запущенных контейнеров. Добавление флага -a покажет все контейнеры, включая остановленные.
4) Команда **docker stop** используется для остановки контейнера:
```Например:
docker stop container_id
```
5) Команда **docker rm** удаляет контейнер:
```Например:
docker rm container_id
```
6) Команда **docker images** выводит список загруженных образов.
7) Команда **docker rmi** удаляет образ:
```Например:
docker rmi image_id
```
8) Команда **docker exec** используется для выполнения команды в запущенном контейнере:
```Например:
docker exec -it container_id /bin/bash
```
9) Команда **docker logs** выводит логи контейнера:
```Например:
docker logs container_id
```
10) Команда **docker stats** выводит статистику использования ресурсов контейнера.
11) Команда **docker build .** создаёт образы Docker из файла Dockerfile и «контекста».

* [Справочная информация по docker командам (например "docker buid -t carpart_docker:0.0.1 .")](https://docs.docker.com/engine/reference/run/)
* [Docker Командная строка](https://digitology.tech/docs/docker/engine/reference/commandline/index.html)

### Основные флаги Docker команд:
1) Загрузка образа и создание контейнера:
**-t** - Устанавливает имя и (при желании) тег образа. 
```Например:
# задает имя "myapp" с тегом "latest".
-t myapp:latest
```
2) Запуск контейнера:
- **-d**: Запускает контейнер в фоновом режиме (демон).
- **-i**: Включает интерактивный режим (stdin) для контейнера.
- **-t**: Подключает tty (терминал) контейнера к вашему терминалу.

3) Порты и сеть:
- **-p**: Пробрасывает порты между хостом и контейнером:
```Например:
#  позволяет доступ к порту 80 контейнера через порт 8080 на хосте.
-p 8080:80
```
- **--network**: Определяет сеть, к которой присоединен контейнер:
```Например:
#  подключает контейнер к сети с именем "my_network".
--network=my_network
```

### Основные команды управления контейнерами (Docker Compose):
После создания файла docker-compose.yml, вы можете использовать следующие основные команды Docker Compose для управления вашим приложением:
- docker-compose start: Запускает остановленный контейнер.
- docker-compose stop: Останавливает работающий контейнер.
- docker-compose restart: Перезапускает контейнер.
- docker-compose pause: Приостанавливает выполнение контейнера.
- docker-compose unpause: Возобновляет выполнение приостановленного контейнера.
- docker-compose kill: Прерывает выполнение контейнера насильно.
- docker-compose rm: Удаляет контейнер.
- docker-compose rmi: Удаляет образ.
- docker-compose up -d: Создание контейнера в demon описанного в docker-compose.yml
- docker-compose up: Запускает приложение на основе файла docker-compose.yml. Если контейнеры не существуют, они будут созданы, и приложение будет запущено в фоновом режиме.
- docker-compose up <container_name>: Содание контейнера и запуск приложения на основе файла docker-compose.yml 
- docker-compose down: Останавливает и удаляет все контейнеры, сети и объемы, связанные с приложением, определенным в файле docker-compose.yml.
- docker-compose ps: Показывает статус всех контейнеров(сервисов), определенных в файле docker-compose.yml.
- docker-compose logs: Отображает логи всех контейнеров(сервисов) в реальном времени.
- docker-compose exec <service-name> <command>: Запускает команду внутри контейнера сервиса. Например, docker-compose exec app bash запустит интерактивный shell в контейнере app.

#### Удаление контейнеров и образов
    -f, --force: Принудительное удаление контейнера, даже если он запущен. Используется вместе с командой docker rm.
    -v, --volumes: Удаление контейнера вместе с его привязанными Docker-томами (volumes). Используется вместе с командой docker rm.
    -l, --link: Удаление контейнера и всех его связей с другими контейнерами. Используется вместе с командой docker rm.
    -f, --force: Принудительное удаление образа, даже если он используется контейнерами. Используется вместе с командой docker rmi.
    -q, --quiet: Вывод только идентификаторов образов без дополнительной информации. Используется вместе с командой docker rmi.

#### Флаги при запуске контейнера
    -d: Запускает контейнер в фоновом режиме (демон).
    -i: Включает интерактивный режим (stdin) для контейнера.
    -t: Подключает tty (терминал) контейнера к вашему терминалу.

Эти флаги при запуске контейнера позволяют настраивать его режим работы для конкретных задач.

## Структура Dockerfile
```
Dockerfile имеет следующую структуру:
# Комментарии начинаются с символа "#"
# Базовый образ
FROM base_image:tag
# Установка дополнительных пакетов
RUN apt-get update && apt-get install -y package_name
# Копирование файлов в образ
COPY source_path destination_path
# Установка переменных окружения
ENV variable_name=value
# Указание рабочей директории
WORKDIR /app
# Открытие порта
EXPOSE port_number
# Запуск команды при запуске контейнера
CMD ["command", "arg1", "arg2"]
```

### Основные команды Dockerfile
Инструкция **FROM** указывает базовый образ, на основе которого будет создаваться ваш образ. Этот образ может быть официальным образом из репозитория Docker Hub или собранным другими пользователями. 
```Пример:
FROM ubuntu:20.04
```
Инструкция **LABEL** описывает метаданные. Например — сведения о том, кто создал и поддерживает образ.
```Пример:
LABEL authors="Artem"
```
Инструкция **RUN** выполняет команду внутри контейнера во время создания образа. Это может использоваться для установки пакетов, обновления системы и выполнения других операций. 
```Пример:
RUN apt-get update && apt-get install -y package_name
```
Инструкция **COPY** копирует файлы из исходной директории на вашем компьютере внутрь контейнера. 
```Пример:
COPY source_path destination_path
```
Инструкция **ENV** устанавливает переменные окружения в контейнере. Это может быть использовано для настройки конфигурации приложения. 
```Пример:
ENV MY_ENV_VARIABLE=my_value
```
Инструкция **WORKDIR** устанавливает рабочую директорию для всех последующих инструкций в Dockerfile. 
```Пример:
WORKDIR /app
```
Инструкция **EXPOSE** указывает порт, который контейнер будет слушать при запуске. Однако это не пробрасывает порт на хост машину, это просто информационная метка. 
```Пример:
EXPOSE 80
```
Инструкция **CMD** задает команду, которая будет выполнена при запуске контейнера. Это может быть команда запуска вашего приложения. 
```Пример:
CMD ["command", "arg1", "arg2"]
```
Инструкция **ADD** копирует файлы и папки в контейнер, может распаковывать локальные .tar-файлы, а так же получать на вход URL и скачивать файл внутрь image.
```Пример:
# Копирование локальных файлов с хоста в целевую директорию
COPY /source/path  /destination/path
ADD /source/path  /destination/path

# Загрузка внешнего файла и копирование его в целевую директорию
ADD http://external.file/url  /destination/path

# Копирование и распаковка локального сжатого файла
ADD source.file.tar.gz /destination/path
```
Инструкция **WORKDIR** задаёт рабочую директорию для следующей инструкции.
```Пример:
WORKDIR /opt/app
```
Инструкция **ARG** задаёт переменные для передачи Docker во время сборки образа.
```Пример:
# Добавить пользователя приложения
ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER
```
Инструкция **ENTRYPOINT** предоставляет команду с аргументами для вызова во время выполнения контейнера. Аргументы не переопределяются.
```Пример:
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]
```
Инструкция **VOLUME** создаёт точку монтирования для работы с постоянным хранилищем.
```Пример:
VOLUME ["/var/www", "/var/log/apache2", "/etc/apache2"]
```

### Пример Dockerfile:
```
# В качестве родителя используем Python v3.8 основанный на Ubuntu
FROM python:3.8

# Просим Python не писать .pyc файлы
ENV PYTHONDONTWRITEBYTECODE 1

# Просим Python не буферизовать stdin/stdout
ENV PYTHONUNBUFFERED 1

# Задаем рабочую директорию
WORKDIR /opt/app

# Копируем файл с зависимостями
COPY ./req.txt /opt/app/requirements.txt

# Устанавливаем зависимости
RUN pip install -r /opt/app/requirements.txt

# Копируем исходный код приложения
COPY ./src /opt/app

# Говорим что надо открыть снаружи порт 8000
EXPOSE 8000

# Команда которая должна быть выполнена при запуске контейнера
CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]

```
### Ссылки для Dockerfile
* [Шпаргалка по Dockerfile](https://devops.org.ru/dockerfile-summary)
* [Справочная информация по Dockerfile (Правый столбик со всеми командами)](https://docs.docker.com/engine/reference/builder/#dockerfile-reference)
* [Пример multi-module сборки (baeldung)](https://www.baeldung.com/ops/docker-cache-maven-dependencies#caching-for-multi-module-maven-projects)

### Ошибки при использовании .dockerignore
- Ошибка 1: Исключение Dockerfile или .dockerignore
```
# Не надо исключать эти файлы. Они нужны Docker для процесса сборки, и игнорирование их может привести к неожиданным результатам.
Dockerfile
.dockerignore
```
- Ошибка 2: Исключение нужных файлов
- Ошибка 3: Исключение всего (всех файлов и директорий) используя звуздочку (*)
```
# Исключение всего
*

# Пример НЕ игнорирования файлов т.е. включение в docker-образ
!.dockerignore
!Dockerfile
!src/
```

### Решения возможных ошибок:
1. **org.postgresql.util.PSQLException: Connection to localhost:5433 refused**
> Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections. 
> When you are running a container (docker) then: **localhost** - the container you are in.**host.docker.internal** - the host of the docker (your localhost).
> So, when ever you are trying to call the localhost you should use host.docker.internal.
> If you sometimes run in container and sometimes as a stand alone, you need to use a flag of some kind (spring profile/property),
> and use it to select the appropriate host.
* [spring boot app in docker image error Connection to localhost:5433 refused](https://stackoverflow.com/questions/72673047/spring-boot-app-in-docker-image-error-connection-to-localhost5433-refused)


### Ссылки для Docker-compose
* [Официальный сайт](https://docs.docker.com/)
* [Капитан грузового судна, или Как начать использовать Docker в своих проектах](https://tproger.ru/translations/how-to-start-using-docker)
* [Docker и Docker Compose: Полное руководство для начинающих](https://dzen.ru/a/ZSJCuZCwPUXxX3TD)
* [Overview of best practices for writing Dockerfiles](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/#run)
* [Compose file](https://docs.docker.com/compose/compose-file/)
* [Overview of Docker Compose (официальный сайт)](https://docs.docker.com/compose/)
* [Варианты запуска docker-compose up](https://docs.docker.com/engine/reference/commandline/compose_up/)
* [Шпаргалка по DockerCompose](https://devops.org.ru/dockercompose-summary)
* [Полное практическое руководство по Docker: с нуля до кластера на AWS](https://habr.com/ru/articles/310460/)
* [Поняв Docker](https://habr.com/ru/articles/277699/)
* [Полная автоматизация «development» среды с помощью docker-compose](https://habr.com/ru/articles/322440/)
* [Docker-compose: идеальное рабочее окружение](https://habr.com/ru/articles/346086/)
* [Добавляем все, что связано с БД. (Часть 1) - "Java-проект от А до Я"](https://javarush.com/groups/posts/3262-java-proekt-ot-a-do-ja-dobavljaem-vse-chto-svjazano-s-bd-chastjh-1)
* [Добавляем все, что связано с БД. (Часть 2) - "Java-проект от А до Я"](https://javarush.com/groups/posts/3264-java-proekt-ot-a-do-ja-dobavljaem-vse-chto-svjazano-s-bd-chastjh-2)

### Ссылки для testcontainers
* [Пример с использованием "static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>" - Тестирование с помощью Testcontainers: как поднять в контейнере тестовую базу](https://sysout.ru/testirovanie-s-pomoshhyu-testcontainers-ili-kak-podnyat-v-kontejnere-testovuyu-bazu/)
* [Baeldung - DB Integration Tests with Spring Boot and Testcontainers](https://www.baeldung.com/spring-boot-testcontainers-integration-test)
* [Baeldung - использование @ServiceConnection для автоконфигурации настроек для подключения к БД (spring.datasource.url/username/password и т.д.)](https://www.baeldung.com/spring-boot-built-in-testcontainers)
* [Habr - Testcontainers: тестирование с реальными зависимостями(пример использования @AutoConfigureTestDatabase)](https://habr.com/ru/articles/700286/)
* [Использование управления жизненным циклом контейнеров](https://www.atomicjar.com/2022/08/testcontainers-and-junit-integration/)
* [Для тестирования баз данных Testcontainers предоставляют специальную поддержку JDBC URL](https://java.testcontainers.org/modules/databases/jdbc/)

### Ссылка для dockerignore
* [Кк использовать .dockerignore - файл (см. раздел Common Pitfalls and How to Avoid Them)](https://hn.mrugesh.dev/how-to-use-a-dockerignore-file-a-comprehensive-guide-with-examples)

### Общие ссылки про Docker
* [Глоссарий Docker Docs](https://docs.docker.com/glossary/#base-image)
* [Запуск приложения с profile в docker-е](https://www.baeldung.com/spring-boot-docker-start-with-profile)
* [Habr - Изучаем Docker(шесть частей)](https://habr.com/ru/companies/ruvds/articles/438796/)
* [Multi-stage builds (Многоступенчатая сборка)](https://docs.docker.com/build/building/multi-stage/)
* [GitHub примеры multi-stage builds](https://github.com/docker/awesome-compose)

[logo_PgAdmin_DB_Connection]: E:\Education\Programming\Java\CarPart\docker\image\db.connection\pgadmin.png "Через PgAdmin (в браузере)"
[logo_IDEA_DB_Connection]: E:\Education\Programming\Java\CarPart\docker\image\db.connection\idea.png "Через IDEA"
[logo_docker_compose_ps]: E:\Education\Programming\Java\CarPart\docker\image\docker-compose\ps.png "Результат работы команды docker-compose ps"
[logo_docker_build]: E:\Education\Programming\Java\CarPart\docker\image\docker\docker_build.png "Пример запуска команды docker build для multi-module сборки"