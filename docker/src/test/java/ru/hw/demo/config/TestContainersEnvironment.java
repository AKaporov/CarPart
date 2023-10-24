package ru.hw.demo.config;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.hw.demo.containers.PostgresTestContainer;

@Testcontainers
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)  // Без поднятия приложения
public class TestContainersEnvironment {
    @Container
    public static PostgreSQLContainer<?> postgresTestContainer = PostgresTestContainer.getInstance();
}
