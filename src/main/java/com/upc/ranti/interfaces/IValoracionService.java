package com.upc.ranti.interfaces;

import com.upc.ranti.dtos.UsuarioArticuloValoracionDto;
import com.upc.ranti.dtos.ValoracionDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IValoracionService {
    public ValoracionDto grabarValoracion(ValoracionDto valoracionDto);
    public List<ValoracionDto> findValoracionsByUsuarioEvaluado_UsuarioId(Long usuarioId);
    public List<ValoracionDto> findValoracionsByUsuarioEvaluado_UsuarioIdOrderByFechaHoraValoracion(@Param("usuarioId") Long usuarioId);
    public List<UsuarioArticuloValoracionDto> obtenerUsuarioConArticulosValoracionMinima();
    public List<UsuarioArticuloValoracionDto> obtenerUsuarioConArticulosValoracionMaxima();


}
