package com.upc.ranti.repositories;

import com.upc.ranti.entities.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EtiquetaRepositorio extends JpaRepository<Etiqueta, Long> {
    @Query("SELECT e.nombre, COUNT(e) AS conteo FROM Articulo a JOIN a.etiquetas e GROUP BY e.nombre ORDER BY conteo DESC")
    List<Object[]> findTopEtiquetasUsadas();
}
