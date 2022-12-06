package com.danielme.spring.testcontainers.reuse;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MySQLContainerClass2Test extends MySQLContainerBaseTest {

    @Test
    void testMySQLContainerIsRunning() {
        assertThat(mySQLContainer.isRunning()).isTrue();
    }

}
