package com.danielme.spring.testcontainers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
@Testcontainers
class MySQLCustomContainerTest {

    @Autowired
    private DataSource dataSource;

    @Container
    private static final GenericContainer mySQLContainer = new GenericContainer<>("mysql:8.0.30")
            .withEnv("MYSQL_ROOT_PASSWORD", "pass")
            .withEnv("MYSQL_DATABASE", "testcontainer")
            .withEnv("MYSQL_USER", "user")
            .withEnv("MYSQL_PASSWORD", "pass")
            .withExposedPorts(3306)
            .waitingFor(Wait.forLogMessage(".*mysqld: ready for connections.*", 2))
            .withCopyFileToContainer(MountableFile.forClasspathResource("init.sql"), "/docker-entrypoint-initdb.d/schema.sql");

    @DynamicPropertySource
    private static void setupProperties(DynamicPropertyRegistry registry) {
        String url = "jdbc:mysql://localhost:" + mySQLContainer.getMappedPort(3306) + "/testcontainer";
        registry.add("spring.datasource.url", () -> url);
        registry.add("spring.datasource.username", () -> "user");
        registry.add("spring.datasource.password", () -> "pass");
    }

    @Test
    void testTableExists() throws SQLException {
        TableTestAssertion.assertTableExists(dataSource);
    }

}
