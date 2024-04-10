package com.restapi.api.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.api.entitys.ImageData;

public interface ImageDataRepository extends JpaRepository<ImageData, String> {
    Optional<ImageData> findByFileName(String fileName);
    List<ImageData> findByFileNameLike(String fileName);
}