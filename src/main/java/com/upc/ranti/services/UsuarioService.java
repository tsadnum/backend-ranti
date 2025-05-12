package com.upc.ranti.services;

import com.upc.ranti.dtos.UsuarioDto;
import com.upc.ranti.dtos.UsuariosReportDto;
import com.upc.ranti.entities.Rol;
import com.upc.ranti.entities.Usuario;
import com.upc.ranti.exceptions.ResourceNotFoundException;
import com.upc.ranti.interfaces.IUsuarioService;
import com.upc.ranti.repositories.ArticuloRepositorio;
import com.upc.ranti.repositories.PostRepositorio;
import com.upc.ranti.repositories.RolRepositorio;
import com.upc.ranti.repositories.UsuarioRepositorio;
import com.upc.ranti.security.JwtUtilService;
import com.upc.ranti.security.SecurityUser;
import com.upc.ranti.security.CustomUserDetailsService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ArticuloRepositorio articuloRepositorio;

    @Autowired
    private PostRepositorio postRepositorio;


    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Override
    @Transactional
    public UsuarioDto grabarUsuario(UsuarioDto usuarioDto) {
        if (usuarioDto.getPassword() == null || usuarioDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }

        Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));

        // Obtener el rol predeterminado "ROL_USUARIO"
        Rol rolUsuario = rolRepositorio.findByNombre("ROL_USUARIO")
                .orElseThrow(() -> new RuntimeException("Rol predeterminado no encontrado"));

        usuario.agregarRolPredeterminado(rolUsuario);

        Usuario guardar = usuarioRepositorio.save(usuario);

        return modelMapper.map(guardar, UsuarioDto.class);
    }

    @Override
    @Transactional
    public String authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (authentication.isAuthenticated()) {
            SecurityUser securityUser = (SecurityUser) customUserDetailsService.loadUserByUsername(username);
            return jwtUtilService.generateToken(securityUser);
        }

        throw new RuntimeException("Autenticación fallida");
    }

    @Override
    @Transactional
    public List<UsuariosReportDto> obtenerUsuariosConPromedioCalifacion() {
        return usuarioRepositorio.obtenerUsuariosConPromedioCalifacion();
    }

    @Override
    @Transactional
    public UsuarioDto actualizarUsuario(String username, UsuarioDto usuarioDto) {
        Usuario usuarioExistente = usuarioRepositorio.findByUsername(username);

        usuarioDto.setUsuarioId(usuarioExistente.getUsuarioId());


        modelMapper.map(usuarioDto, usuarioExistente);

        Usuario usuarioActualizado = usuarioRepositorio.save(usuarioExistente);

        return modelMapper.map(usuarioActualizado, UsuarioDto.class);
    }

    @Override
    @Transactional
    public void eliminarUsuario(String username) {
        Usuario usuario = usuarioRepositorio.findByUsername(username);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario con username " + username + " no encontrado.");
        }

        usuario.setEstado("eliminado");

        usuario.getRoles().clear();

        usuario.getArticulos().forEach(articulo -> {
            articulo.setEstado("eliminado");

            articuloRepositorio.save(articulo);
        });

        usuario.getPosts().forEach(post -> {
            post.setEstado("eliminado");

            postRepositorio.save(post);
        });

        usuarioRepositorio.save(usuario);
    }


    @Override
    @Transactional

    public UsuarioDto agregarRolAUsuario(Long usuarioId, Long id) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Rol rol = rolRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));

        if (usuario.getRoles().contains(rol)) {
            throw new IllegalArgumentException("El usuario ya tiene asignado este rol");
        }

        usuario.getRoles().add(rol);

        Usuario usuarioActualizado = usuarioRepositorio.save(usuario);

        return modelMapper.map(usuarioActualizado, UsuarioDto.class);
    }

    @Override
    @Transactional
    public UsuarioDto removerRolDeUsuario(Long usuarioId, Long id) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Rol rol = rolRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));

        if (!usuario.getRoles().contains(rol)) {
            throw new IllegalArgumentException("El usuario no tiene asignado este rol");
        }

        usuario.getRoles().remove(rol);

        Usuario usuarioActualizado = usuarioRepositorio.save(usuario);

        return modelMapper.map(usuarioActualizado, UsuarioDto.class);
    }


    @Override
    @Transactional
    public UsuarioDto suspenderUsuario(Long id) {

        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format("Usuario con id %d no encontrado", id)));

        if (usuario.getEstado().equals("eliminado"))
            throw new RuntimeException("Usuario se encuentra eliminado");

        usuario.setEstado("suspendido");

        usuario.getArticulos().forEach(articulo -> articulo.setEstado("no_disponible"));

        Usuario usuarioFinal = usuarioRepositorio.save(usuario);

        return modelMapper.map(usuarioFinal, UsuarioDto.class);
    }


}
