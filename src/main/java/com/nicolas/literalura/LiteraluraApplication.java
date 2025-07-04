package com.nicolas.literalura;

import com.nicolas.literalura.dto.LibroDto;
import com.nicolas.literalura.service.GutendexService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Component
	public static class AplicacionPrincipal implements CommandLineRunner {

		private final GutendexService gutendexService;
		private final Scanner scanner = new Scanner(System.in);
		private final List<LibroDto> librosGuardados = new ArrayList<>();

		public AplicacionPrincipal(GutendexService gutendexService) {
			this.gutendexService = gutendexService;
		}

		@Override
		public void run(String... args) throws Exception {
			boolean salir = false;

			while (!salir) {
				System.out.println("\n=== Menú ===");
				System.out.println("1. Buscar libro por título");
				System.out.println("2. Listar todos los libros buscados");
				System.out.println("3. Listar autores de libros buscados");
				System.out.println("4. Listar autores vivos en un año");
				System.out.println("5. Salir");
				System.out.print("Elija una opción: ");
				String opcion = scanner.nextLine();

				switch (opcion) {
					case "1":
						buscarLibro();
						break;
					case "2":
						listarLibros();
						break;
					case "3":
						listarAutores();
						break;
					case "4":
						listarAutoresVivos();
						break;
					case "5":
						salir = true;
						System.out.println("Saliendo...");
						break;
					default:
						System.out.println("Opción inválida, intente nuevamente.");
				}
			}
		}

		private void buscarLibro() {
			System.out.print("Ingrese el título del libro a buscar: ");
			String titulo = scanner.nextLine();

			try {
				Optional<LibroDto> libro = gutendexService.buscarLibroPorTitulo(titulo);
				if (libro.isPresent()) {
					System.out.println("Libro encontrado:");
					System.out.println(libro.get());
					librosGuardados.add(libro.get());
				} else {
					System.out.println("No se encontraron libros con ese título.");
				}
			} catch (Exception e) {
				System.out.println("Error al buscar el libro: " + e.getMessage());
			}
		}

		private void listarLibros() {
			if (librosGuardados.isEmpty()) {
				System.out.println("No hay libros guardados.");
			} else {
				System.out.println("\nLibros buscados:");
				librosGuardados.forEach(System.out::println);
			}
		}

		private void listarAutores() {
			if (librosGuardados.isEmpty()) {
				System.out.println("No hay libros guardados para mostrar autores.");
				return;
			}
			System.out.println("\nAutores de libros buscados:");
			librosGuardados.stream()
					.map(LibroDto::getAutor)
					.distinct()
					.forEach(System.out::println);
		}

		private void listarAutoresVivos() {
			System.out.print("Ingrese el año para consultar autores vivos: ");
			try {
				int anio = Integer.parseInt(scanner.nextLine());
				if (librosGuardados.isEmpty()) {
					System.out.println("No hay libros guardados para mostrar autores.");
					return;
				}
				System.out.println("\nAutores vivos en el año " + anio + ":");
				librosGuardados.stream()
						.map(LibroDto::getAutor)
						.filter(autor -> autor.viveEnAnio(anio))
						.distinct()
						.forEach(System.out::println);
			} catch (NumberFormatException e) {
				System.out.println("Por favor ingrese un número válido.");
			}
		}
	}
}
