package com.upc.ranti.services;

import com.upc.ranti.dtos.ArticuloDto;
import com.upc.ranti.entities.Articulo;
import com.upc.ranti.exceptions.ResourceNotFoundException;
import com.upc.ranti.interfaces.IArticuloService;
import com.upc.ranti.repositories.ArticuloRepositorio;
import com.upc.ranti.repositories.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticuloService implements IArticuloService {
    @Autowired
    private ArticuloRepositorio articuloRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public ArticuloDto grabarArticulo(ArticuloDto articuloDto){

        Articulo articulo = modelMapper.map(articuloDto, Articulo.class);
        Articulo guardar = articuloRepositorio.save(articulo);
        return modelMapper.map(guardar, ArticuloDto.class);
    }

    @Override
    public List<ArticuloDto> obtenerArticulosDisponibles() {
        List<Articulo> articulos = articuloRepositorio.findByPublicoTrueAndEstado("disponible");

        return articulos.stream()
                .map(articulo -> modelMapper.map(articulo, ArticuloDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticuloDto> obtenerArticulosPublicosPorUsuario(Long usuarioId) {
        if (!usuarioRepositorio.existsById(usuarioId)) {
            throw new RuntimeException("Usuario no encontrado");
        }

        return articuloRepositorio.findByUsuarioUsuarioIdAndPublicoTrue(usuarioId)
                .stream()
                .map(articulo -> modelMapper.map(articulo, ArticuloDto.class))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public List<ArticuloDto> filtrarPorEtiquetas(List<String> nombresEtiquetas) {
        List<Articulo> articulos = articuloRepositorio.findByEtiquetasNombreIn(nombresEtiquetas);

        return articulos.stream()
                .map(articulo -> modelMapper.map(articulo, ArticuloDto.class))
                .collect(Collectors.toList());
    }

    public void actualizarVisibilidad(Long articuloId, boolean publico, String username) {
        Articulo articulo = articuloRepositorio.findById(articuloId)
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado"));
        articulo.setPublico(publico);
        articuloRepositorio.save(articulo);
    }

    public void eliminarArticulosInapropiados() {
        List<Articulo> inapropiados = articuloRepositorio.findByInapropiadoTrue();
        articuloRepositorio.deleteAll(inapropiados);
    }

    @Override
    public void marcarComoInapropiado(Long id) {
        Articulo articulo = articuloRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado con ID: " + id));
        articulo.setInapropiado(true);
        articuloRepositorio.save(articulo);
    }

    @Override
    public Map<String, Long> contarArticulosPorEstado() {
        List<Object[]> resultados = articuloRepositorio.countArticulosByEstado();
        Map<String, Long> conteo = new HashMap<>();

        for (Object[] resultado : resultados) {
            String estado = (String) resultado[0];
            Long cantidad = (Long) resultado[1];
            conteo.put(estado, cantidad);
        }

        return conteo;
    }

    @Override
    public ArticuloDto actualizarEstadoArticulo(Long articuloId, String nuevoEstado, String username) {
        Articulo articulo = articuloRepositorio.findById(articuloId)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

        // Validar que el usuario autenticado sea el propietario
        if (!articulo.getUsuario().getUsername().equals(username)) {
            throw new RuntimeException("No tienes permiso para actualizar este artículo");
        }

        articulo.setEstado(nuevoEstado);

        Articulo articuloActualizado = articuloRepositorio.save(articulo);

        return modelMapper.map(articuloActualizado, ArticuloDto.class);
    }

    @Override
    public List<ArticuloDto> findArticulosByUsuario_UsuarioId(Long usuarioId) {
        if (!usuarioRepositorio.existsById(usuarioId)) {
            throw new RuntimeException("Usuario no encontrado");
        }

        List<Articulo> articulos = articuloRepositorio.findArticulosByUsuario_UsuarioId(usuarioId);

        return articulos.stream()
                .map(articulo -> modelMapper.map(articulo, ArticuloDto.class))
                .collect(Collectors.toList());
    }


}
