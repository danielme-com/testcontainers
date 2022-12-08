package com.danielme.spring.testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class TableTestAssertion {

    static void assertTableExists(DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             ResultSet resultSet = conn.prepareStatement("SHOW TABLES").executeQuery();) {
            if (resultSet.next()) {
                String table = resultSet.getString(1);
                assertThat(table).isEqualTo("tests");
            } else {
                fail("No results for SHOW TABLES");
            }
        }
    }

}
