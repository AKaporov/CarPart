# Глобальная цель проекта
Создать модульный обучающий проект. Задание для всех модулей единое и приведено ниже. Каждый новый модуль реализует новую технологию, которая описывается в ReadMe.md

# Задание для всех модулей
На картинках показан frontend сайта по-продаже запчастей. Необходимо
спроектировать и реализовать REST API между сайтом и backend частью.

## Что входит:
1. Проектирование REST JSON API и его реализация в виде сервиса
2. Проектирование схемы БД и реализация слоя доступа к данным в сервисе

## Что не входит:
3. Разработка frontend части
4. Аутентификация, rate limiting API и т.д.
5. Хранилище для медиа контента (картинки и т.д.)

Пользователь может ввести в поле поиска номер запчасти и нажать кнопку "Search" для поиска. Сайт отображает список 
найденных запчастей по номеру.

Пользователь может задать фильтры по:
1. марке
2. модели
3. году выпуска 
4. двигателю 
нажать кнопку "Search". Сайт отображает список запчастей соответствующих критериям поиска.

Пользователь может нажать на любую запись, чтобы перейти на страницу
карточки запчасти и посмотреть дополнительную информацию:
1. Полное наименование
2. Описание
3. Фотографии
4. Стоимость
5. Каталожный номер (Vendor Code)
6. Артикул (SKU)
7. Производитель
8. Страна производства
9. Рейтинг
10. Возможные аналоги на замену
и другие, см. рисунок.

## Технические требования
1. Тестовое задание (REST API) должен быть реализован на платформе JDK 11 или выше
2. Язык реализации Java
3. В качестве базового фреймворка возможно использование spring-boot 2+, micronaut или любого другого.
4. Сборка и запуск проекта с помощью Maven
5. Слой доступа к данным может использовать Hibernate/JPA/MyBatis и т.д.
6. PostgreSQL в качестве базы данных
7. Решение должно включать SQL DDL для формирования схемы БД
8. Решение может включать тестовый набор данных для БД (seed) в виде SQL дампа

## Дополнительные очки если
1. Использование юнит тестирования
2. Dockerfile для сборки и запуска образа в контейнере
3. Запуск всего решения API + PostgreSQL через docker compose или kubernetes

## Команды для запуска в браузере (после запуска приложения):
* [пример фильтра по марке = "Ural"](http://localhost:8080/api/v1/carparts?brandName=Ural)
* [пример фильтра по марке "Ural", модели "Ural-4320" и году выпуска 1977](http://localhost:8080/api/v1/carparts?brandName=Ural&modelName=Ural-4320&yearRelease=1977)
* [пример ошибочного фильтра (марка "Ural", модель "Ural-4320) и год выпуска 19](http://localhost:8080/api/v1/carparts?brandName=Ural&modelName=Ural-4320&yearRelease=19)
* [пример поиска по каталожному номеру "URL-4320-02"](http://localhost:8080/api/v1/carparts?VendorCode=URL-4320-02)
* [пример получения полной информации о запасной части по её каталожному номеру "URL-4320-01"](http://localhost:8080/api/v1/carparts/URL-4320-01)

## Общие полезные ссылки
* [Spring Boot 2.7 [Русский]](https://runebook.dev/ru/docs/spring_boot/-index-)
* [Querydsl](https://querydsl.com/static/querydsl/4.4.0/reference/html_single/)
* [Введение в Querydsl](https://javascopes.com/intro-to-querydsl-f08c8bfd/)
* [Query Introduction(Описание Query DSL)](https://www.komapper.org/docs/reference/query/introduction/)
* [Initialize a Database](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization)
* [**spring.sql.init.mode**](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/api/org/springframework/boot/sql/init/DatabaseInitializationMode.html)

## Используемые версии
Spring Boot - 3.1.2 (Иначе в Spring Data JDBC не работают методы репозитория по полям "AggregateReference<>")
* [Common Application Properties](https://docs.spring.io/spring-boot/docs/3.1.2/reference/html/application-properties.html#appendix.application-properties.data)
* [Spring Boot 3.1.0 Configuration Changelog](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.1.0-Configuration-Changelog)

# Подсказки
## Добавление и получение данных из своих properties
@PropertySource({"classpath:jdbc.properties"}) см. JdbcConfiguration.java в SPRING-DATA-JDBC

* [Команда для выкатывания tag-ов в github ("git push --tags")](https://www.thisprogrammingthing.com/2013/git-tags-not-showing-in-github/)