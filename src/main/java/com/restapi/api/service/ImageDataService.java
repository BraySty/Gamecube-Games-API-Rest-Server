package com.restapi.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.restapi.api.entitys.ImageData;
import com.restapi.api.entitys.Mensaje;
import com.restapi.api.repositorio.ImageDataRepository;
import com.restapi.api.util.ImageUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Los metodos dan error en eclipse pero funcionan, 
 * el servidor fue programado primariamente en 
 * Visual Studio Code.
 */
@Service
public class ImageDataService {

    @Autowired
    private ImageDataRepository imageDataRepo;

    public ResponseEntity<Mensaje> save(MultipartFile file) throws IOException {
        Optional<ImageData> imageData = imageDataRepo.findByFileName(file.getOriginalFilename());
        if (imageData.isPresent()) {
			return new ResponseEntity<>(new Mensaje("Ya existe una imagen con nombre " + file.getOriginalFilename() + " existe"), HttpStatus.CONFLICT);
		} else {
            uploadImage(file);
			return new ResponseEntity<>(new Mensaje("Se ha añadido la imagen con nombre " + file.getOriginalFilename()), HttpStatus.OK);
		}
    }

    public ResponseEntity<?> read(String fileName) {
        String wildcard = "%";
        List<ImageData> imageData = imageDataRepo.findByFileNameLike(wildcard + fileName + wildcard);
        if (!imageData.isEmpty()) {
            byte[] image = downloadImage(imageData.get(0).getFileName());
		    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
        } else {
            return new ResponseEntity<>(new Mensaje(notFoundResponse(fileName)), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> update(String fileName, MultipartFile file) throws IOException {
        Optional<ImageData> imageData = imageDataRepo.findByFileName(fileName);
        if (imageData.isPresent()) {
            imageDataRepo.delete(imageData.get());
            uploadImage(file);
		    return new ResponseEntity<>(new Mensaje("Se ha actualizado la imagen con nombre " + fileName), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Mensaje(notFoundResponse(fileName)), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> delete(String fileName) {
        Optional<ImageData> imageData = imageDataRepo.findByFileName(fileName);
        if (imageData.isPresent()) {
            imageDataRepo.delete(imageData.get());
		    return new ResponseEntity<>(new Mensaje("Se ha eliminado la imagen con nombre " + fileName), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Mensaje(notFoundResponse(fileName)), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Crea un String concatenado con el nombre del archivo.
     * @param fileName El nombre del archivo.
     * @return El String concatenado.
     */
    public String notFoundResponse(String fileName) {
        return "La imagen con nombre " + fileName + " no existe.";

    }

    /**
     * Sube una imagen a la base de datos.
     * @param file La imagen que se sube a la base de datos.
     * @return ImageData de la imagen que se acaba de guardar.
     */
    public ImageData uploadImage(MultipartFile file) throws IOException {
		ImageData imageData = new ImageData();
		imageData.setFileName(file.getOriginalFilename());
		imageData.setType(file.getContentType());
		imageData.setImageDataByte(ImageUtil.compressImage(file.getBytes()));
		return imageDataRepo.save(imageData);
	}
	
    /**
     * Recoge la imagen almacenada en la base de datos y la regresa
     * @param fileName El nombre de la imagen.
     * @return byte[] con los datos de la imagen.
     */
	public byte[] downloadImage(String fileName){
        Optional<ImageData> imageData = imageDataRepo.findByFileName(fileName);
        if (imageData.isPresent()) {
            return ImageUtil.decompressImage(imageData.get().getImageDataByte());
        }
        return new byte[0];
    }

}