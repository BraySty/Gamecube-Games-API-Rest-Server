package com.restapi.api.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "imageData", catalog = "gamecube")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_Image", unique = true, nullable = false)
    private String id;
    @Column(name = "name", unique = true)
    private String fileName;
    @Column(name = "type")
    private String type;
    @Lob
    @Column(name = "imagedata", unique = false, nullable = false, length = 1048576)
    private byte[] imageDataByte;
}