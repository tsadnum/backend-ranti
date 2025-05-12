package com.upc.ranti.interfaces;

import com.upc.ranti.dtos.ArticuloDto;

import java.util.List;
import java.util.Map;

public interface IArticuloService {
    public ArticuloDto grabarArticulo(ArticuloDto articuloDto);
    public List<ArticuloDto> obtenerArticulosDisponibles();
    public List<ArticuloDto> obtenerArticulosPublicosPorUsuario(Long usuarioId);
    public List<ArticuloDto> filtrarPorEtiquetas(List<String> nombresEtiquetas);
    void actualizarVisibilidad(Long articuloId, boolean publico, String username);
    void marcarComoInapropiado(Long id);
    void eliminarArticulosInapropiados();
    Map<String, Long> contarArticulosPorEstado();
    public ArticuloDto actualizarEstadoArticulo(Long articuloId, String nuevoEstado, String username);
    public List<ArticuloDto> findArticulosByUsuario_UsuarioId(Long usuarioId);

}
