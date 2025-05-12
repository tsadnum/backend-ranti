package com.upc.ranti.services;

import com.upc.ranti.dtos.ImagenDto;
import com.upc.ranti.entities.Imagen;
import com.upc.ranti.interfaces.IImagenService;
import com.upc.ranti.repositories.ImagenRepositorio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagenService implements IImagenService {
    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public ImagenDto grabarImagen(ImagenDto imagenDto) {
        Imagen imagen = modelMapper.map(imagenDto, Imagen.class);
        Imagen guardar = imagenRepositorio.save(imagen);
        return modelMapper.map(guardar, ImagenDto.class);
    }
}
