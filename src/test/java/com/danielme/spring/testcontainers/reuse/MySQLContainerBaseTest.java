package com.danielme.spring.testcontainers.reuse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;
import java.util.Map;

@SpringBootTest
public class MySQLContainerBaseTest {

    @Autowired
    protected DataSource dataSource;

    @ServiceConnection
    protected static final MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8.0.30")
            .withDatabaseName("testcontainer")
            .withUsername("user")
            .withPassword("pass")
            .withTmpFs(Map.of("/var/lib/mysql", "rw"))
            .withReuse(true); //to use this, enable testcontainers.reuse.enable in testcontainers properties
    // see https://www.testcontainers.org/features/reuse/

    static {
        mySQLContainer.start();
    }

    //As of Spring Boot 3.1, with @ServiceConnection you don't need the following method to set up the connection
   /*
    @DynamicPropertySource
    private static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }
    */
}
