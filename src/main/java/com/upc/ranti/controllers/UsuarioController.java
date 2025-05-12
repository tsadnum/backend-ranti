package com.upc.ranti.controllers;

import com.upc.ranti.dtos.UsuarioDto;
import com.upc.ranti.dtos.UsuariosReportDto;
import com.upc.ranti.interfaces.IUsuarioService;
import com.upc.ranti.security.JwtAuthenticationResponse;
import com.upc.ranti.security.JwtUtilService;
import com.upc.ranti.security.LoginRequest;
import com.upc.ranti.security.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilService jwtUtilService;

    @PostMapping("/grabar")
    public ResponseEntity<UsuarioDto> grabarUsuario(@RequestBody UsuarioDto usuarioDto) {
        log.info("Grabando usuario: {}", usuarioDto.getNombre());
        UsuarioDto registrado = usuarioService.grabarUsuario(usuarioDto);
        log.info("Usuario grabado con éxito: {}", registrado);
        return new ResponseEntity<>(registrado, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.info("Intento de login para el usuario: {}", loginRequest.getUsername());
        try {
            // Autenticación del usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Generación del token JWT
            String token = jwtUtilService.generateToken((SecurityUser) authentication.getPrincipal());
            log.info("Login exitoso para el usuario: {}", loginRequest.getUsername());

            // Retornar el token
            return ResponseEntity.status(HttpStatus.OK).body(new JwtAuthenticationResponse(token));
        } catch (BadCredentialsException e) {
            log.warn("Credenciales inválidas para el usuario: {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @GetMapping("/report")
    public ResponseEntity<List<UsuariosReportDto>> obtenerUsuariosConPromedioCalifacion() {
        log.info("Obteniendo reporte de usuarios con promedio de calificaciones");
        return ResponseEntity.ok(usuarioService.obtenerUsuariosConPromedioCalifacion());
    }

    @PutMapping
    public ResponseEntity<UsuarioDto> actualizarUsuario(@RequestBody UsuarioDto usuarioDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("Actualizando información del usuario autenticado: {}", username);

        UsuarioDto usuarioDtoActualizado = usuarioService.actualizarUsuario(username, usuarioDto);

        log.info("Usuario actualizado con éxito: {}", usuarioDtoActualizado);


        return ResponseEntity.ok(usuarioDtoActualizado);
    }

    @PutMapping("/eliminar")
    public ResponseEntity<Void> eliminarUsuario() {
        // Obtener el nombre del usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("Eliminando usuario autenticado: {}", username);

        usuarioService.eliminarUsuario(username);
        log.info("Usuario eliminado con éxito: {}", username);

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{usuarioId}/roles/{id}")
    public ResponseEntity<UsuarioDto> agregarRolAUsuario(
            @PathVariable Long usuarioId,
            @PathVariable Long id
    ) {
        log.info("Agregando rol con ID: {} al usuario con ID: {}", id, usuarioId);

        try {
            UsuarioDto usuarioActualizado = usuarioService.agregarRolAUsuario(usuarioId, id);
            log.info("Rol agregado exitosamente al usuario: {}", usuarioActualizado);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            log.error("Error al agregar rol: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{usuarioId}/roles/{id}")
    public ResponseEntity<UsuarioDto> removerRolDeUsuario(
            @PathVariable Long usuarioId,
            @PathVariable Long id
    ) {
        log.info("Removiendo rol con ID: {} del usuario con ID: {}", id, usuarioId);

        try {
            UsuarioDto usuarioActualizado = usuarioService.removerRolDeUsuario(usuarioId, id);
            log.info("Rol removido exitosamente del usuario: {}", usuarioActualizado);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            log.error("Error al remover rol: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/suspender/{id}")
    public ResponseEntity<UsuarioDto> suspenderUsuario(@PathVariable Long id) {
        log.info("Suspendiendo usuario con ID: {}", id);

        UsuarioDto usuarioDTO = usuarioService.suspenderUsuario(id);
        log.info("Usuario suspendido con éxito: {}", usuarioDTO);

        return ResponseEntity.ok(usuarioDTO);
    }

}
