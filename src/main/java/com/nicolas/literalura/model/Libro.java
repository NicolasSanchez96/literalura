package com.nicolas.literalura.model;

import jakarta.persistence.*;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne  // Quité cascade = CascadeType.ALL
    @JoinColumn(name = "autor_id")
    private Autor autor;

    private String idioma;

    private String isbn;

    public Libro() {}

    public Libro(String titulo, Autor autor, String idioma, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.isbn = isbn;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Título: " + titulo +
                "\nAutor: " + (autor != null ? autor.getNombre() : "Desconocido") +
                "\nIdioma: " + (idioma != null ? idioma : "Desconocido") +
                "\nISBN: " + (isbn != null ? isbn : "N/A");
    }
}
