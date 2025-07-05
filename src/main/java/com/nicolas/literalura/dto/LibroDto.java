package com.nicolas.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LibroDto {

    @JsonAlias("title")
    private String title;

    @JsonAlias("authors")
    private List<AutorDto> authors;

    @JsonAlias("languages")
    private List<String> languages;

    @JsonAlias("download_count")
    private Integer downloadCount;

    // Como no hay ISBN en la respuesta, no lo incluimos (puedes agregar si en tu API existe)
    // private String isbn;

    public LibroDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AutorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AutorDto> authors) {
        this.authors = authors;
    }

    public AutorDto getAutor() {
        return (authors != null && !authors.isEmpty()) ? authors.get(0) : null;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    // Método conveniente para obtener el primer idioma o null
    public String getIdioma() {
        return (languages != null && !languages.isEmpty()) ? languages.get(0) : null;
    }

    @Override
    public String toString() {
        String autor = getAutor() != null ? getAutor().getName() : "Desconocido";
        String idioma = getIdioma() != null ? getIdioma() : "Desconocido";
        return "Título: " + title +
                "\nAutor: " + autor +
                "\nIdioma: " + idioma +
                "\nDescargas: " + downloadCount;
    }
}
