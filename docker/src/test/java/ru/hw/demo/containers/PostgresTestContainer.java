package ru.hw.demo.containers;

import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Objects;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {
    private static final String DOCKER_IMAGE_NAME = "postgres:13.2-alpine";
    private static final String DATABASE_NAME = "CarPartDB_Docker_Test";
    private static PostgreSQLContainer postgreSQLContainer;

    public PostgresTestContainer() {
        super(DOCKER_IMAGE_NAME);
    }

    public static PostgreSQLContainer getInstance() {
        if (Objects.isNull(postgreSQLContainer)) {
            postgreSQLContainer = new PostgreSQLContainer(DOCKER_IMAGE_NAME)
                    .withDatabaseName(DATABASE_NAME)
                    .withUsername("cp_usr")
                    .withPassword("cp_PostgreSQL");

        }

        return postgreSQLContainer;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", postgreSQLContainer.getJdbcUrl());
        System.setProperty("DB_USERNAME", postgreSQLContainer.getUsername());
        System.setProperty("DB_PASSWORD", postgreSQLContainer.getPassword());
    }

    @Override
    public void stop() {
        super.stop();
    }
}
