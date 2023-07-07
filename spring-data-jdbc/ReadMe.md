# В проекте применяется Spring Data JDBC (Java Database Connectivity)
Поддержка интерфейсов JDBC для реляционных баз данных. 

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jdbc</artifactId>
		</dependency>

Spring Data предлагает к интерфейсу Repository также интерфейс CrudRepository, поэтому в Репозиториях можно 
использовать CrudRepository<>. 

Если используется метод из Repository/CrudRepository, то надо помнить, что в domain - сущности указание названия таблицы 
(в @Table(value = "TABLE_NAME")), колонки (@Column(value = "COLUM_NAME")) регистрозависимо на схему БД (schema.sql). 
В собственноручно написанных запросах регистрозависимость игнорируется. 

* [Spring Data JDBC](https://spring.io/projects/spring-data-jdbc)
* [Введение в Spring Data JDBC (https://for-each.dev/)](https://for-each.dev/lessons/b/-spring-data-jdbc-intro)
* [Введение в Spring Data JDBC (Habr)](https://habr.com/ru/companies/otus/articles/531332/)
* [Spring Data JDBC Reference Documentation](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#repositories)
* [Class JdbcTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html)
* [Spring Boot + Spring Data JDBC + Microsoft SQL Server](https://www.knowledgefactory.net/2023/02/spring-boot-spring-data-jdbc-microsoft-sql-server-build-rest-crud-apis.html)