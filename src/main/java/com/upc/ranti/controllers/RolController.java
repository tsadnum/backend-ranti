package com.upc.ranti.controllers;


import com.upc.ranti.dtos.RolDto;
import com.upc.ranti.interfaces.IRolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/roles")

public class RolController {
    @Autowired
    private IRolService rolService;

    @PostMapping("/grabar")
    public ResponseEntity<RolDto> grabarArticulo(@RequestBody RolDto rolDto) {
        log.info("Grabando rol: {}", rolDto.getNombre());
        return ResponseEntity.ok(rolService.grabarRol(rolDto));
    }
}
