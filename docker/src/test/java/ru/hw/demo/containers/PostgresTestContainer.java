package ru.hw.demo.containers;

import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Objects;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {
    private static final String DOCKER_IMAGE_NAME = "postgres:13.2-alpine";
    private static final String DATABASE_NAME = "CarPartDB_Docker_Test";
    private static final String USER_NAME = "cp_usr";
    private static final String PASSWORD = "cp_PostgreSQL";
    private static PostgresTestContainer postgresTestContainer;

    public PostgresTestContainer() {
        super(DOCKER_IMAGE_NAME);
    }

    public static PostgreSQLContainer getInstance() {
        if (Objects.isNull(postgresTestContainer)) {
            postgresTestContainer = new PostgresTestContainer().
                    withDatabaseName(DATABASE_NAME)
                    .withUsername(USER_NAME)
                    .withPassword(PASSWORD)
                    .withReuse(true);  // withResure() с флажком true - держит контейнер в активном состоянии до тех пор, пока не исполнятся все тестовые методы, а также дает возможность переиспользовать контейнер для каждого теста без его затухания и поднятия нового экземпляра.
        }

        return postgresTestContainer;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", postgresTestContainer.getJdbcUrl());
        System.setProperty("DB_USERNAME", postgresTestContainer.getUsername());
        System.setProperty("DB_PASSWORD", postgresTestContainer.getPassword());

//        TestPropertyValues.of("DB_URL".concat(postgresTestContainer.getJdbcUrl()),
//                "DB_USERNAME".concat(postgresTestContainer.getUsername()),
//                "DB_PASSWORD".concat(postgresTestContainer.getPassword()))
//                .applyTo(????);
    }

    @Override
    public void stop() {
        super.stop();
    }
}
