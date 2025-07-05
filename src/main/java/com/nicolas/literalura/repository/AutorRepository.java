package com.nicolas.literalura.repository;

import com.nicolas.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    List<Autor> findByNombre(String nombre);


    List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanOrAnioFallecimientoIsNull(int anioNacimiento, int anioFallecimiento);
}
