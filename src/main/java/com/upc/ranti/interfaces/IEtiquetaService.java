package com.upc.ranti.interfaces;

import com.upc.ranti.dtos.EtiquetaUsoDto;

import java.util.List;

public interface IEtiquetaService {
    public List<EtiquetaUsoDto> obtenerTop10EtiquetasMasUsadas();
}
