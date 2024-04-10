package com.restapi.api.controller;

import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.restapi.api.entitys.Mensaje;
import com.restapi.api.service.ImageDataService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ImageDataController {

    private final ImageDataService imageService;
    
    @PostMapping("/images")
    public ResponseEntity<Mensaje> uploadImage(@RequestParam("productImage")MultipartFile file) throws IOException {
        return imageService.save(file);
    }
    
    @GetMapping("/images/{fileName}")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        return imageService.read(fileName);
	}

    @PutMapping("/images/{fileName}")
	public ResponseEntity<?> updateImage(@PathVariable String fileName, @RequestParam("productImage")MultipartFile file) throws IOException {
        return imageService.update(fileName, file);
	}

    @DeleteMapping("/images/{fileName}")
	public ResponseEntity<?> deleteImage(@PathVariable String fileName) {
        return imageService.delete(fileName);
	}
}