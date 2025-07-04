package com.nicolas.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nicolas.literalura.dto.RespuestaDto;
import org.springframework.stereotype.Service;

@Service
public class GutendexService {

    private final HttpClient httpClient;

    public GutendexService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public String buscarLibros(String terminoBusqueda) throws IOException, InterruptedException {
        String encodedBusqueda = java.net.URLEncoder.encode(terminoBusqueda, java.nio.charset.StandardCharsets.UTF_8);
        String url = "https://gutendex.com/books/?search=" + encodedBusqueda;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public RespuestaDto obtenerLibros(String terminoBusqueda) throws IOException, InterruptedException {
        String json = buscarLibros(terminoBusqueda);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, RespuestaDto.class);
    }

}
