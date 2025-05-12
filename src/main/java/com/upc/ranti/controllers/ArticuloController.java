package com.upc.ranti.controllers;

import com.upc.ranti.dtos.ArticuloDto;
import com.upc.ranti.interfaces.IArticuloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {
    @Autowired
    private IArticuloService articuloService;

    @PostMapping("/grabar")
    public ResponseEntity<ArticuloDto> grabarArticulo(@RequestBody ArticuloDto articuloDto) {
        log.info("Grabando artículo: {}", articuloDto.getNombre());
        return ResponseEntity.ok(articuloService.grabarArticulo(articuloDto));

    }
    @GetMapping("/disponibles")
    public ResponseEntity<List<ArticuloDto>> obtenerArticulosDisponibles() {
        log.info("Obteniendo lista de artículos disponibles");
        return ResponseEntity.ok(articuloService.obtenerArticulosDisponibles());
    }

    @GetMapping("usuario/{usuarioId}/publicos")
    public ResponseEntity<List<ArticuloDto>> obtenerArticulosPublicosPorUsuario(@PathVariable Long usuarioId) {
        log.info("Obteniendo artículos públicos del usuario con ID: {}", usuarioId);
        List<ArticuloDto> articulos = articuloService.obtenerArticulosPublicosPorUsuario(usuarioId);

        if (articulos.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<ArticuloDto>());
        }
        return ResponseEntity.ok(articulos);
    }

    @PutMapping("/{id}/visibilidad")
    public ResponseEntity<String> cambiarVisibilidad(@PathVariable Long id, @RequestParam boolean publico, Principal principal) {
        log.info("Cambiando visibilidad del artículo con ID: {} a {}", id, publico ? "público" : "privado");
        articuloService.actualizarVisibilidad(id, publico, principal.getName());
        return ResponseEntity.ok("Visibilidad actualizada");
    }

    @GetMapping("/filtrar/etiquetas")
    public ResponseEntity<List<ArticuloDto>> filtrarArticulosPorEtiquetas(@RequestParam List<String> etiquetas) {
        log.info("Filtrando artículos por etiquetas: {}", etiquetas);
        List<ArticuloDto> resultado = articuloService.filtrarPorEtiquetas(etiquetas);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}/marcar-inapropiado")
    public ResponseEntity<String> marcarArticuloComoInapropiado(@PathVariable Long id) {
        log.info("Marcando artículo con ID: {} como inapropiado", id);
        articuloService.marcarComoInapropiado(id);
        return ResponseEntity.ok("Artículo marcado como inapropiado.");
    }


    @DeleteMapping("eliminar/inapropiados")
    public ResponseEntity<String> eliminarArticulosInapropiados() {
        log.info("Eliminando artículos marcados como inapropiados");
        articuloService.eliminarArticulosInapropiados();
        return ResponseEntity.ok("Artículos inapropiados eliminados correctamente.");
    }

    @GetMapping("/estadisticas/estado")
    public ResponseEntity<Map<String, Long>> obtenerEstadisticasPorEstado() {
        log.info("Obteniendo estadísticas por estado de los artículos");
        return ResponseEntity.ok(articuloService.contarArticulosPorEstado());
    }

    @PutMapping("/{articuloId}/estado")
    public ResponseEntity<ArticuloDto> actualizarEstadoArticulo(
            @PathVariable Long articuloId,
            @RequestParam String nuevoEstado) {
        log.info("Actualizando estado del artículo con ID: {} a {}", articuloId, nuevoEstado);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ArticuloDto articuloActualizado = articuloService.actualizarEstadoArticulo(articuloId, nuevoEstado, username);

        return ResponseEntity.ok(articuloActualizado);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ArticuloDto>> obtenerTodosArticulosPorUsuario(@PathVariable Long usuarioId) {
        log.info("Iniciando solicitud para obtener todos los artículos del usuario con ID: {}", usuarioId);
        List<ArticuloDto> articulos = articuloService.findArticulosByUsuario_UsuarioId(usuarioId);
        return ResponseEntity.ok(articulos);
    }
}
