package com.upc.ranti.services;

import com.upc.ranti.dtos.EtiquetaDto;
import com.upc.ranti.dtos.EtiquetaUsoDto;
import com.upc.ranti.entities.Etiqueta;
import com.upc.ranti.interfaces.IEtiquetaService;
import com.upc.ranti.repositories.EtiquetaRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EtiquetaService implements IEtiquetaService {

    @Autowired
    EtiquetaRepositorio etiquetaRepositorio;

    @Override
    public List<EtiquetaUsoDto> obtenerTop10EtiquetasMasUsadas() {
        List<Object[]> resultados = etiquetaRepositorio.findTopEtiquetasUsadas();
        return resultados.stream()
                .map(obj -> new EtiquetaUsoDto((String) obj[0], (Long) obj[1]))
                .limit(10)
                .collect(Collectors.toList());
    }



}
