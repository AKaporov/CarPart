# В проекте применяется Spring Data JDBC (Java Database Connectivity)
#### Поддержка интерфейсов JDBC для реляционных баз данных:
    <dependency>
	    <groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-jdbc</artifactId>
    </dependency>

## Spring Data JDBC does not support many-to-one or many-to-many relationships. In order to model these, use IDs:
* [Spring Data JDBC does not support many-to-one or many-to-many relationships](https://spring.io/blog/2018/09/24/spring-data-jdbc-references-and-aggregates)
* [Spring Data JDBC – Modelling and working with aggregates](https://thorben-janssen.com/spring-data-jdbc-aggregates/)

Spring Data предлагает к интерфейсу Repository также интерфейс CrudRepository, поэтому в Репозиториях можно 
использовать CrudRepository<>. 

Если используется метод из Repository/CrudRepository, то надо помнить, что в domain - сущности указание названия таблицы 
(в @Table(value = "TABLE_NAME")), колонки (@Column(value = "COLUM_NAME")) регистрозависимо на схему БД (schema.sql). 
В собственноручно написанных запросах регистрозависимость игнорируется. 

#### query vs queryForObject vs queryForList vs queryForMap vs queryForRowSet:
    When to use query() : As it supports different callback handlers to extract the results, when we need to get multiple rows data as results and convert them to Java Domain Models ( for example List<User>), query() is right fit in those situations.
    When to use queryForObject() : This operation also supports for RowMapper callback handler. When you wants to get single row results and convert it to Domain Model Or you wants to get single value as results, queryForObject() is right fit in those situations.
    When to use queryFoList() : Like query() operation queryFoList() also returns multiple rows data, but there is no callback handlers support. Sometimes we need selected column results of multiple rows (projections) required for instant use, we no need to convert them as Domain Model. queryFoList() is right fit in those situations.
    When to use queryForMap() : Like queryForObject() operation queryForMap() also returns single value or multiple column values of one database table row. Sometimes we need selected column results of single row (projections) required for instant use, we no need to convert them as Domain Model. queryFoMap() is right fit in those situations.
    When to use queryForRowSet() : All the above operations works on top of ResultSet to get the results. queryForRowSet() loads all the results to CachedRowSet wraps it as SqlRowSet implementation and returns, which is mirror interface for RowSet representing a disconnected variant of ResultSet data. If you would like to work in disconnected fashion or to work with RowSet for specific reasons, can be used queryForRowSet() operation (refer example 1 under queryForObject() to understand how to get RowSet).

A single JdbcTemplate query operation may not fit in all situations. In this guide we have covered several examples on 
Spring JdbcTemplate query, queryForObject, queryForList, queryForMap, queryForRowSet operations to understand how to use 
them depends on requirement.

In Short :
1. query() – is used to get multiple rows results as list.
2. queryForObject() – is used to get single value or single row results.
3. queryForList() – is used to get selected columns data of multiple rows as List.
4. queryForMap() – is used to get selected columns data of single row as Map.
5. queryForRowSet() – is used when you wants to work with RowSets instead of ResultSet.

JdbcOperations – интерфейс для JdbcTemplate – и лучше использовать его.

## Reference Material

* [Spring Data JDBC](https://spring.io/projects/spring-data-jdbc)
* [9.1. Why Spring Data JDBC? (reference documentation)](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#jdbc.why)
* [Введение в Spring Data JDBC (https://for-each.dev/)](https://for-each.dev/lessons/b/-spring-data-jdbc-intro)
* [Введение в Spring Data JDBC (Habr)](https://habr.com/ru/companies/otus/articles/531332/)
* [Spring Data JDBC Reference Documentation](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#repositories)
* [9.7. Query Methods](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#jdbc.query-methods)
* [Class JdbcTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html)
* [Spring Boot + Spring Data JDBC + Microsoft SQL Server](https://www.knowledgefactory.net/2023/02/spring-boot-spring-data-jdbc-microsoft-sql-server-build-rest-crud-apis.html)
* [Описание JdbcTemplate](https://docs.spring.io/spring-framework/docs/5.3.23/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html)

### Spring Blog
* [Introducing Spring Data JDBC](https://spring.io/blog/2018/09/17/introducing-spring-data-jdbc)
* [Spring Data JDBC, References, and Aggregates](https://spring.io/blog/2018/09/24/spring-data-jdbc-references-and-aggregates)

### Spring Docs
* [Spring Data Auditing](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#auditing)
* [Lifecycle Events](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#jdbc.events)
* [Entity Callbacks](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#entity-callbacks)
* [Transaction Management](https://docs.spring.io/spring-framework/docs/4.2.x/spring-framework-reference/html/transaction.html)
* [MyBatis Integration](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#jdbc.mybatis)

### GitHub
* [spring-data-jdbc](https://github.com/spring-projects/spring-data-relational)
* [spring-data-examples/jdbc](https://github.com/spring-projects/spring-data-examples/tree/main/jdbc)

### StackOverflow
* [Spring Data JDBC / Spring Data JPA vs Hibernate](https://stackoverflow.com/questions/42470060/spring-data-jdbc-spring-data-jpa-vs-hibernate/42488593#42488593)
* [Can I do pagination with spring-data-jdbc?](https://stackoverflow.com/questions/55570077/can-i-do-pagination-with-spring-data-jdbc)
* [PagingAndSortingRepository methods throw error when used with spring data jdbc](https://stackoverflow.com/questions/53088927/pagingandsortingrepository-methods-throw-error-when-used-with-spring-data-jdbc/53089091#53089091)

### Pluralsight
* [Spring Framework: Overview of Spring Data](https://app.pluralsight.com/library/courses/spring-framework-overview-spring-data/table-of-contents)

### Baeldung
* [Spring Data JPA @Query](https://www.baeldung.com/spring-data-jpa-query)
* [Introduction to AssertJ](https://www.baeldung.com/introduction-to-assertj)