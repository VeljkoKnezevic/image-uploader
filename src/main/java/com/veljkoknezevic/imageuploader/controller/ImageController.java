package com.veljkoknezevic.imageuploader.controller;

import com.veljkoknezevic.imageuploader.entity.Image;
import com.veljkoknezevic.imageuploader.repository.ImageRepository;
import com.veljkoknezevic.imageuploader.utility.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageController {
    private ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostMapping("/image/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        Image image = new Image(file.getOriginalFilename(), file.getContentType(), ImageUtility.compressImage(file.getBytes()));

        imageRepository.save(image);

        return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully: " + file.getOriginalFilename());
    }

    @GetMapping("/image/data/{name}")
    public Image getImageDetails(@PathVariable("name") String name) {
        Image dbImage = imageRepository.findByName(name);

        return new Image(dbImage.getName(), dbImage.getType(), ImageUtility.decompressImage(dbImage.getImage()));
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) {
        Image dbImage = imageRepository.findByName(name);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(dbImage.getType()))
                .body(ImageUtility.decompressImage(dbImage.getImage()));
    }
}
