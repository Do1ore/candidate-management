package com.example.candidatemanagement.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void init();

    String storeCv(MultipartFile file);

    String storePhoto(MultipartFile file);

    Path loadCv(String filename);

    Path loadPhoto(String filename);


}
