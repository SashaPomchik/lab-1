package com.example.demo.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class BaseIntegrationTest {

    private static final boolean IS_CI = System.getenv("GITHUB_ACTIONS") != null;

    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            !IS_CI ? new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass")
                    : null;

    @BeforeAll
    static void startContainer() {
        if (!IS_CI) {
            POSTGRESQL_CONTAINER.start();
            System.setProperty("TEST_DB_URL", POSTGRESQL_CONTAINER.getJdbcUrl());
            System.setProperty("TEST_DB_USERNAME", POSTGRESQL_CONTAINER.getUsername());
            System.setProperty("TEST_DB_PASSWORD", POSTGRESQL_CONTAINER.getPassword());
        }
    }
}
