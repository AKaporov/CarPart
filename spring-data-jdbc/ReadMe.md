# В проекте применяется Spring Data JDBC
поддержка интерфйсов JDBC для реляционных баз данных


		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jdbc</artifactId>
		</dependency>

Spring Data предлагает к интерфейсу Repository также интерфейс CrudRepository, поэтому в Репозиториях можно 
использовать CrudRepository<>.