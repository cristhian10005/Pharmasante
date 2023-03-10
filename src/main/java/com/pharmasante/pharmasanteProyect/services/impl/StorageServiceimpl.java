package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.EntitiesDto.ProductoDTO;
import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.services.IStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Service
public class StorageServiceimpl implements IStorageService {

    @Value("${media.location}")
    private String mediaLocation;
    @Value("${img.path}")
    private String imagePath;
    private Path rootLocation;

    @Override
    @PostConstruct
    public void init() throws IOException {
        rootLocation = Paths.get(mediaLocation);
        Files.createDirectories(rootLocation);
    }

    @Override
    public String store(ProductoDTO p) {
    try {
        if (p == null){
            throw new ProductException("Producto vacio","Formato no valido");
        }
        String fileName = p.getNombreImg();
        Path destinationFile = rootLocation.resolve(Paths.get(fileName))
                .normalize().toAbsolutePath();
        try (InputStream inputStream = new ByteArrayInputStream(decodificar(p.getBytesImg()))){
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }

        return imagePath+fileName;

    }catch (IOException e){
        throw new ProductException(e.getMessage(),"Formato no valido");
    }
    }

    private byte[] decodificar(String bytesImg){
        try {
            byte bytes[] = Base64.getDecoder().decode(bytesImg);
            return bytes;
        } catch (IllegalArgumentException e) {
            throw new ProductException(e.getMessage(),"Formato no valido");
        }
    }
}
