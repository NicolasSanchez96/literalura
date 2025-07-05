package com.nicolas.literalura.service;

import com.nicolas.literalura.model.Autor;
import com.nicolas.literalura.model.Libro;
import com.nicolas.literalura.repository.AutorRepository;
import com.nicolas.literalura.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public long contarLibrosPorIdioma(String idioma) {
        return libroRepository.countByIdioma(idioma);
    }

    public Libro buscarYGuardarLibro(String titulo, String nombreAutor, String idioma, String isbn) {
        // Buscar autor en la base de datos
        List<Autor> autoresExistentes = autorRepository.findByNombre(nombreAutor);
        Autor autor;

        if (!autoresExistentes.isEmpty()) {
            autor = autoresExistentes.get(0);
        } else {
            autor = new Autor();
            autor.setNombre(nombreAutor);
            // Si tenés otros datos, setéalos aquí
            autor = autorRepository.save(autor);
        }

        // Crear libro con autor gestionado
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setIdioma(idioma);
        libro.setIsbn(isbn);

        return libroRepository.save(libro);
    }
}
