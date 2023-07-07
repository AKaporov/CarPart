# В проекте применяется Spring Data JDBC (Java Database Connectivity)
Поддержка интерфейсов JDBC для реляционных баз данных. 


		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jdbc</artifactId>
		</dependency>

Spring Data предлагает к интерфейсу Repository также интерфейс CrudRepository, поэтому в Репозиториях можно 
использовать CrudRepository<>.

* [Spring Data](https://spring.io/projects/spring-data)
* [Spring Data JDBC](https://spring.io/projects/spring-data-jdbc)
* [Введение в Spring Data JDBC (https://for-each.dev/)](https://for-each.dev/lessons/b/-spring-data-jdbc-intro)
* [Введение в Spring Data JDBC (Habr)](https://habr.com/ru/companies/otus/articles/531332/)
* [Spring Data JDBC Reference Documentation](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#repositories)
* [Class JdbcTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html)
