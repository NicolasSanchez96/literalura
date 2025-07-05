package com.nicolas.literalura.model;

import jakarta.persistence.*;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer anioNacimiento;
    private Integer anioFallecimiento;

    public Autor() {}

    public Autor(String nombre, Integer anioNacimiento, Integer anioFallecimiento) {
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
        this.anioFallecimiento = anioFallecimiento;
    }

    // Getters y setters...

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public Integer getAnioFallecimiento() {
        return anioFallecimiento;
    }

    public void setAnioFallecimiento(Integer anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }

    public boolean viveEnAnio(int anio) {
        boolean nacidoAntesOEnAnio = anioNacimiento != null && anio >= anioNacimiento;
        boolean noFallecidoAun = anioFallecimiento == null || anio <= anioFallecimiento;
        return nacidoAntesOEnAnio && noFallecidoAun;
    }

    @Override
    public String toString() {
        return nombre + " (" + (anioNacimiento != null ? anioNacimiento : "?") + " - " +
                (anioFallecimiento != null ? anioFallecimiento : "Presente") + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autor)) return false;
        Autor autor = (Autor) o;
        return id != null && id.equals(autor.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
