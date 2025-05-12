package com.upc.ranti.repositories;

import com.upc.ranti.entities.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticuloRepositorio extends JpaRepository<Articulo, Long> {

    List<Articulo> findByPublicoTrueAndEstado(String estado);
    List<Articulo> findByUsuarioUsuarioIdAndPublicoTrue(Long usuarioId);
    @Query("SELECT a.estado, COUNT(a) FROM Articulo a GROUP BY a.estado")
    List<Object[]> countArticulosByEstado();
    List<Articulo> findByEtiquetasNombreIn(List<String> nombresEtiquetas);


    List<Articulo> findByInapropiadoTrue();

    public List<Articulo> findArticulosByUsuario_UsuarioId(Long usuarioId);
}
