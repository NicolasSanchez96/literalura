package com.nicolas.literalura.service;

import com.nicolas.literalura.dto.LibroDto;
import com.nicolas.literalura.dto.RespuestaDto;
import com.nicolas.literalura.model.Autor;
import com.nicolas.literalura.model.Libro;
import com.nicolas.literalura.repository.AutorRepository;
import com.nicolas.literalura.repository.LibroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class GutendexService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public GutendexService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public Optional<Libro> buscarLibroPorTituloYGuardar(String tituloBuscado) throws IOException, InterruptedException {
        String url = "https://gutendex.com/books/?search=" + URLEncoder.encode(tituloBuscado, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        RespuestaDto respuesta = objectMapper.readValue(response.body(), RespuestaDto.class);

        if (respuesta.getResults() != null && !respuesta.getResults().isEmpty()) {
            LibroDto libroDto = respuesta.getResults().get(0);

            Autor autor = null;
            if (libroDto.getAuthors() != null && !libroDto.getAuthors().isEmpty()) {
                var autorDto = libroDto.getAuthors().get(0);

                // Buscar autores existentes por nombre
                List<Autor> autoresExistentes = autorRepository.findByNombre(autorDto.getName());

                if (!autoresExistentes.isEmpty()) {
                    // Usar el primer autor existente (ya persistido)
                    autor = autoresExistentes.get(0);
                } else {
                    // Crear y guardar autor nuevo solo si no existe
                    autor = new Autor();
                    autor.setNombre(autorDto.getName());
                    autor.setAnioNacimiento(autorDto.getBirthYear());
                    autor.setAnioFallecimiento(autorDto.getDeathYear());
                    autor = autorRepository.save(autor);
                }
            }

            Libro libro = new Libro();
            libro.setTitulo(libroDto.getTitle());
            libro.setAutor(autor);

            // Poner idioma si está disponible
            if (libroDto.getLanguages() != null && !libroDto.getLanguages().isEmpty()) {
                libro.setIdioma(libroDto.getLanguages().get(0));
            } else {
                libro.setIdioma("Desconocido");
            }

            libro.setIsbn(null); // O lo que corresponda

            libro = libroRepository.save(libro);

            return Optional.of(libro);
        }
        return Optional.empty();
    }
}
