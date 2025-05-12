package com.upc.ranti.controllers;

import com.upc.ranti.dtos.ImagenDto;
import com.upc.ranti.interfaces.IImagenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/imagen")

public class ImagenController {
    @Autowired
    private IImagenService imagenService;

    @PostMapping("/grabar")
    public ResponseEntity<ImagenDto> grabarImagen(@RequestBody ImagenDto imagenDto) {
        log.info("Grabando imagen: {}", imagenDto.getUrl());
        return ResponseEntity.ok(imagenService.grabarImagen(imagenDto));
    }
}
