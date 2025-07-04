package com.nicolas.literalura.service;

import com.nicolas.literalura.dto.LibroDto;
import com.nicolas.literalura.dto.RespuestaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class GutendexService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GutendexService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public Optional<LibroDto> buscarLibroPorTitulo(String tituloBuscado) throws IOException, InterruptedException {
        String url = "https://gutendex.com/books/?search=" + URLEncoder.encode(tituloBuscado, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        RespuestaDto respuesta = objectMapper.readValue(response.body(), RespuestaDto.class);

        if (respuesta.getResults() != null && !respuesta.getResults().isEmpty()) {
            LibroDto libro = respuesta.getResults().get(0);
            return Optional.of(libro);
        }
        return Optional.empty();
    }
}
