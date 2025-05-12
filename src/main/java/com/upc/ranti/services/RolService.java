package com.upc.ranti.services;

import com.upc.ranti.dtos.RolDto;
import com.upc.ranti.entities.Rol;
import com.upc.ranti.interfaces.IRolService;
import com.upc.ranti.repositories.RolRepositorio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class RolService implements IRolService {

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public RolDto grabarRol(RolDto rolDto) {
        Rol rol = modelMapper.map(rolDto, Rol.class);
        Rol guardar = rolRepositorio.save(rol);
        return modelMapper.map(guardar, RolDto.class);
    }
}
