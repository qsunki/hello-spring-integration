package com.example.hellospringintegration.config;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

class HttpConfigTest {

    @Test
    void httpTest() {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/foo"))
                            .GET() // 기본값이 GET
                            .build();
            // 요청 보내고 응답 받기
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            // 응답 출력
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
