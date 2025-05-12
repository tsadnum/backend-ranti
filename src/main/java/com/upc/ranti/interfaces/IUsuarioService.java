package com.upc.ranti.interfaces;

import com.upc.ranti.dtos.UsuarioDto;
import com.upc.ranti.dtos.UsuariosReportDto;

import java.util.List;


public interface IUsuarioService {
    public UsuarioDto grabarUsuario(UsuarioDto usuarioDto);
    String authenticateUser(String username, String password);
    public List<UsuariosReportDto> obtenerUsuariosConPromedioCalifacion();
    public UsuarioDto actualizarUsuario(String username, UsuarioDto usuarioDto);
    void eliminarUsuario(String username);
    public UsuarioDto agregarRolAUsuario(Long usuarioId, Long id);
    public UsuarioDto removerRolDeUsuario(Long usuarioId, Long id);
    public UsuarioDto suspenderUsuario(Long id);




}
