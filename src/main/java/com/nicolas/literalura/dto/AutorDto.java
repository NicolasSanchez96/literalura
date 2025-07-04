package com.nicolas.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorDto {

    @JsonAlias("name")
    private String name;

    @JsonAlias("birth_year")
    private Integer birthYear;

    @JsonAlias("death_year")
    private Integer deathYear;

    public AutorDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }


    public boolean viveEnAnio(int anio) {
        return (birthYear != null && birthYear <= anio) &&
                (deathYear == null || deathYear >= anio);
    }

    @Override
    public String toString() {
        return "Nombre: " + name +
                ", Nacimiento: " + (birthYear != null ? birthYear : "Desconocido") +
                ", Fallecimiento: " + (deathYear != null ? deathYear : "Desconocido");
    }
}

