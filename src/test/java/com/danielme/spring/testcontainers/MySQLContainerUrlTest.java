package com.danielme.spring.testcontainers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest(properties = "spring.datasource.url=jdbc:tc:mysql:8.0.30:///testcontainer?TC_INITSCRIPT=init.sql&TC_TMPFS=/var/lib/mysql:rw")
class MySQLContainerUrlTest {

    @Test
    void testTableExists(@Autowired DataSource dataSource) throws SQLException {
        TableTestAssertion.assertTableExists(dataSource);
    }

}
