package com.pharmasante.pharmasanteProyect.services;

import com.pharmasante.pharmasanteProyect.EntitiesDto.ProductoDTO;

import java.io.IOException;

public interface IStorageService {
    void init() throws IOException;
    String store(ProductoDTO p);
}
