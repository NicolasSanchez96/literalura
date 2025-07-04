package com.nicolas.literalura;

import com.nicolas.literalura.dto.RespuestaDto;
import com.nicolas.literalura.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private GutendexService gutendexService;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		RespuestaDto respuesta = gutendexService.obtenerLibros("Sherlock Holmes");

		respuesta.getResults().forEach(libro -> {
			System.out.println("Título: " + libro.getTitle());
			if (!libro.getAuthors().isEmpty()) {
				System.out.println("Autor: " + libro.getAuthors().get(0).getName());
			}
			System.out.println("Idiomas: " + libro.getLanguages());
			System.out.println("---------------------------");
		});
	}

}

