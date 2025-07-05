package com.nicolas.literalura;

import com.nicolas.literalura.model.Autor;
import com.nicolas.literalura.model.Libro;
import com.nicolas.literalura.service.GutendexService;
import com.nicolas.literalura.service.LibroService;
import com.nicolas.literalura.repository.AutorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.HashSet;


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
		private final LibroService libroService;
		private final AutorRepository autorRepository;
		private final Scanner scanner = new Scanner(System.in);
		private final List<Libro> librosGuardados = new ArrayList<>();

		public AplicacionPrincipal(GutendexService gutendexService, LibroService libroService, AutorRepository autorRepository) {
			this.gutendexService = gutendexService;
			this.libroService = libroService;
			this.autorRepository = autorRepository;
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
				System.out.println("5. Mostrar cantidad de libros por idioma");
				System.out.println("6. Salir");
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
						mostrarCantidadLibrosPorIdioma();
						break;
					case "6":
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
				Optional<Libro> libroOpt = gutendexService.buscarLibroPorTituloYGuardar(titulo);
				if (libroOpt.isPresent()) {
					Libro libro = libroOpt.get();
					System.out.println("Libro encontrado y guardado:");
					System.out.println("Título: " + libro.getTitulo());
					System.out.println("Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
					System.out.println("ISBN: " + (libro.getIsbn() != null ? libro.getIsbn() : "N/A"));
					System.out.println("Idioma: " + (libro.getIdioma() != null ? libro.getIdioma() : "N/A"));
					librosGuardados.add(libro);
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
				librosGuardados.forEach(libro -> {
					System.out.println("Título: " + libro.getTitulo());
					System.out.println("Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
					System.out.println("ISBN: " + (libro.getIsbn() != null ? libro.getIsbn() : "N/A"));
					System.out.println("Idioma: " + (libro.getIdioma() != null ? libro.getIdioma() : "N/A"));
					System.out.println("---");
				});
			}
		}

		private void listarAutores() {
			if (librosGuardados.isEmpty()) {
				System.out.println("No hay libros guardados para mostrar autores.");
				return;
			}
			System.out.println("\nAutores de libros buscados:");
			librosGuardados.stream()
					.map(Libro::getAutor)
					.distinct()
					.forEach(autor -> System.out.println(autor != null ? autor.getNombre() : "Desconocido"));
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
				// Usamos un Set para evitar imprimir autores repetidos
				Set<String> autoresUnicos = new HashSet<>();
				librosGuardados.stream()
						.map(Libro::getAutor)
						.filter(autor -> autor != null && autor.viveEnAnio(anio))
						.forEach(autor -> autoresUnicos.add(autor.getNombre()));

				if (autoresUnicos.isEmpty()) {
					System.out.println("No se encontraron autores vivos en ese año.");
				} else {
					autoresUnicos.forEach(System.out::println);
				}
			} catch (NumberFormatException e) {
				System.out.println("Por favor ingrese un número válido.");
			}
		}



		private void mostrarCantidadLibrosPorIdioma() {
			System.out.print("Ingrese el idioma para consultar la cantidad de libros: ");
			String idioma = scanner.nextLine();

			long cantidad = libroService.contarLibrosPorIdioma(idioma);
			System.out.println("Cantidad de libros en idioma '" + idioma + "': " + cantidad);
		}
	}
}
