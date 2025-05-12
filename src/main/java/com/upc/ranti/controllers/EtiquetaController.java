package com.upc.ranti.controllers;

import com.upc.ranti.dtos.ArticuloDto;
import com.upc.ranti.dtos.EtiquetaDto;
import com.upc.ranti.dtos.EtiquetaUsoDto;
import com.upc.ranti.services.ArticuloService;
import com.upc.ranti.services.EtiquetaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/etiquetas")
public class EtiquetaController {
    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private EtiquetaService etiquetaService;


    @GetMapping("/filtrar/etiquetas")
    public ResponseEntity<List<ArticuloDto>> filtrarArticulos(@RequestParam List<String> etiquetas) {
        log.info("Filtrando artículos con las etiquetas: {}", etiquetas);
        List<ArticuloDto> resultado = articuloService.filtrarPorEtiquetas(etiquetas);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/top-etiquetas")
    public ResponseEntity<List<EtiquetaUsoDto>> obtenerTopEtiquetas() {
        log.info("Obteniendo las 10 etiquetas más usadas");
        return ResponseEntity.ok(etiquetaService.obtenerTop10EtiquetasMasUsadas());
    }

}
