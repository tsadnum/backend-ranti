package com.upc.ranti.controllers;

import com.upc.ranti.dtos.SolicitudArticuloDto;
import com.upc.ranti.dtos.TotalArticulosPorCiudadDto;
import com.upc.ranti.interfaces.ISolicitudArticuloService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/solicitudes")

public class SolicitudArticuloContoller {
    @Autowired
    private ISolicitudArticuloService solicitudArticuloService;


    @PostMapping("/grabar")
    public ResponseEntity<SolicitudArticuloDto>grabarSolicitudArticulo(@RequestBody SolicitudArticuloDto solicitudArticuloDto) {
        log.info("Grabando nueva solicitud de artículo: {}", solicitudArticuloDto.getSolicitudId());
        return ResponseEntity.ok(solicitudArticuloService.grabarSolicitudArticulo(solicitudArticuloDto));
    }

    @GetMapping("/recibidas/{usuarioSolicitadoId}")
    public ResponseEntity<List<SolicitudArticuloDto>> obtenerSolicitudesRecibidas(@PathVariable Long usuarioSolicitadoId) {
        log.info("Obteniendo solicitudes recibidas para el usuario con ID: {}", usuarioSolicitadoId);
        List<SolicitudArticuloDto> solicitudes = solicitudArticuloService.findSolicitudArticulosByUsuarioSolicitado_UsuarioId(usuarioSolicitadoId);
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/enviadas/{usuarioSolicitanteId}")
    public ResponseEntity<List<SolicitudArticuloDto>> obtenerSolicitudesEnviadas(@PathVariable Long usuarioSolicitanteId) {
        log.info("Obteniendo solicitudes enviadas por el usuario con ID: {}", usuarioSolicitanteId);
        List<SolicitudArticuloDto> solicitudes = solicitudArticuloService.findSolicitudArticulosByUsuarioSolicitante_UsuarioId(usuarioSolicitanteId);
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/propuestas/{usuarioSolicitadoId}/{articuloOfrecidoId}")
    public ResponseEntity<List<SolicitudArticuloDto>> obtenerPropuestasDeIntercambio(
            @PathVariable Long usuarioSolicitadoId,
            @PathVariable Long articuloOfrecidoId) {
        log.info("Obteniendo propuestas de intercambio para el usuario con ID: {} y artículo ofrecido con ID: {}",
                usuarioSolicitadoId, articuloOfrecidoId);
        List<SolicitudArticuloDto> propuestas = solicitudArticuloService.findSolicitudArticulosByUsuarioSolicitado_UsuarioIdAndArticuloOfrecido_ArticuloId(usuarioSolicitadoId, articuloOfrecidoId);
        return ResponseEntity.ok(propuestas);
    }

    @GetMapping("/propuestas-enviadas")
    public List<SolicitudArticuloDto> getPropuestasEnviadasPorArticulo(
            @RequestParam("usuarioSolicitanteId") Long usuarioSolicitanteId,
            @RequestParam("articuloSolicitadoId") Long articuloSolicitadoId) {
        log.info("Obteniendo propuestas enviadas por el usuario con ID: {} para el artículo solicitado con ID: {}",
                usuarioSolicitanteId, articuloSolicitadoId);
        return solicitudArticuloService.findSolicitudArticulosByUsuarioSolicitante_UsuarioIdAndArticuloSolicitado_ArticuloId(
                usuarioSolicitanteId, articuloSolicitadoId);
    }

    @GetMapping("/totalPorCiudad")
    public ResponseEntity<List<TotalArticulosPorCiudadDto>> obtenerTotalArticulosSolicitadosPorCiudad() {
        log.info("Obteniendo total de artículos solicitados por ciudad");
        List<TotalArticulosPorCiudadDto> resultados = solicitudArticuloService.obtenerTotalArticulosSolicitadosPorCiudad();
        return ResponseEntity.ok(resultados);
    }


    @PutMapping("/{id}/{decision}")
    public ResponseEntity<String> decidirPropuesta(
            @PathVariable Long id,
            @PathVariable String decision) {

        log.info("Decidiendo sobre la propuesta con ID: {}, decisión: {}", id, decision);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Asumiendo que el username identifica al usuario

        boolean autorizado = solicitudArticuloService.validarUsuarioAutorizado(id, username);
        if (!autorizado) {
            log.warn("Usuario no autorizado: {}", username);
            return ResponseEntity.status(403).body("No estás autorizado para tomar esta decisión.");
        }

        boolean resultado = solicitudArticuloService.decidirPropuesta(id, decision);

        if (resultado) {
            log.info("La solicitud con ID: {} ha sido {}", id, decision.toLowerCase());
            return ResponseEntity.ok("La solicitud ha sido " + decision.toLowerCase());
        } else {
            log.error("Error al procesar la decisión para la solicitud con ID: {}", id);
            return ResponseEntity.badRequest().body("La decisión no se pudo procesar.");
        }
    }

    @PutMapping("/cancelar/{solicitudId}")
    public ResponseEntity<String> cancelarPropuesta(@PathVariable Long solicitudId) {
        log.info("Cancelando propuesta con ID: {}", solicitudId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Obtener el username del usuario autenticado

        log.info("Usuario autenticado: {}", username);


        try {
            solicitudArticuloService.cancelarPropuesta(solicitudId, username); // Pasar username a cancelarPropuesta
            log.info("Propuesta con ID: {} cancelada con éxito", solicitudId);
            return ResponseEntity.status(HttpStatus.OK).body("Propuesta cancelada con éxito.");
        } catch (IllegalArgumentException | SecurityException | IllegalStateException e) {
            log.error("Error al cancelar la propuesta con ID: {}: {}", solicitudId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
