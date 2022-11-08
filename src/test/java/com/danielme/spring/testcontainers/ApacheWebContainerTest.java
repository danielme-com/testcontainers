package com.danielme.spring.testcontainers;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class ApacheWebContainerTest {

    @Container
    private static final GenericContainer httpdContainer = new GenericContainer<>("httpd:latest")
            .withExposedPorts(80);

    @Test
    void testGet() throws Exception {
        String address = "http://" + httpdContainer.getHost() + ":" + httpdContainer.getMappedPort(80);
        HttpRequest localhost = HttpRequest.newBuilder(new URI(address)).build();

        HttpResponse<Void> response = HttpClient.newHttpClient()
                .send(localhost, HttpResponse.BodyHandlers.discarding());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

}
