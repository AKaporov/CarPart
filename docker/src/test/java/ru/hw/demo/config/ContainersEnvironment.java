package ru.hw.demo.config;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.hw.demo.containers.PostgresTestContainer;

@Testcontainers
public class ContainersEnvironment {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = PostgresTestContainer.getInstance();
}
