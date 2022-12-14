package com.danielme.spring.testcontainers.reuse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;

@SpringBootTest
public class MySQLContainerBaseTest {

    @Autowired
    protected DataSource dataSource;

    protected static final MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8.0.30")
            .withDatabaseName("testcontainer")
            .withUsername("user")
            .withPassword("pass")
            .withReuse(true); //to use this, enable testcontainers.reuse.enable in testcontainers properties
    // or set the env var TESTCONTAINERS_REUSE_ENABLED

    static {
        mySQLContainer.start();
    }

    @DynamicPropertySource
    private static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

}
