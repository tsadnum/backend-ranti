package com.upc.ranti.controllers;

import com.upc.ranti.dtos.UsuarioArticuloValoracionDto;
import com.upc.ranti.dtos.ValoracionDto;
import com.upc.ranti.interfaces.IValoracionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/valoracion")
public class ValoracionController {

    @Autowired
    private IValoracionService valoracionService;

    @PostMapping("grabar")
    public ValoracionDto grabarValoracion(@RequestBody ValoracionDto valoracionDto) {
        log.info("Intentando grabar valoración: {}", valoracionDto);
        return valoracionService.grabarValoracion(valoracionDto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ValoracionDto>> getValoracionesByUsuarioEvaluado(@PathVariable Long usuarioId) {
        log.info("Buscando valoraciones para el usuario con ID: {}", usuarioId);
        List<ValoracionDto> valoraciones = valoracionService.findValoracionsByUsuarioEvaluado_UsuarioId(usuarioId);
        return ResponseEntity.ok(valoraciones);
    }

    @GetMapping("/usuario/{usuarioId}/desc")
    public ResponseEntity<List<ValoracionDto>> getValoracionesOrdenadas(@PathVariable Long usuarioId) {
        log.info("Buscando valoraciones ordenadas para el usuario con ID: {}", usuarioId);
        List<ValoracionDto> valoraciones = valoracionService.findValoracionsByUsuarioEvaluado_UsuarioIdOrderByFechaHoraValoracion(usuarioId);
        return ResponseEntity.ok(valoraciones);
    }

    @GetMapping("/usuario/articulos/valoracionMinima")
    public ResponseEntity<List<UsuarioArticuloValoracionDto>> obtenerUsuarioConArticulosValoracionMinima() {
        log.info("Buscando usuarios con artículos y valoración mínima...");
        List<UsuarioArticuloValoracionDto> resultados = valoracionService.obtenerUsuarioConArticulosValoracionMinima();
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/usuario/articulos/valoracionMaxima")
    public ResponseEntity<List<UsuarioArticuloValoracionDto>> obtenerUsuarioConArticulosValoracionMaxima() {
        log.info("Buscando usuarios con artículos y valoración máxima...");
        List<UsuarioArticuloValoracionDto> resultados = valoracionService.obtenerUsuarioConArticulosValoracionMaxima();
        return ResponseEntity.ok(resultados);
    }

}
