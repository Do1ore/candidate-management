package com.example.candidatemanagement.api.service;

import com.example.candidatemanagement.api.configuration.StorageProperties;
import com.example.candidatemanagement.api.exception.StorageException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService implements StorageService {

    private final Path rootCvLocation;
    private final Path rootPhotoLocation;

    @Autowired
    public FileStorageService(StorageProperties properties) {

        if (properties.getCvLocation().trim().isEmpty()) {
            throw new StorageException("File upload location can not be Empty.");
        }
        if (properties.getPhotoLocation().trim().isEmpty()) {
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootCvLocation = Paths.get(properties.getCvLocation());
        this.rootPhotoLocation = Paths.get(properties.getPhotoLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootCvLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String storeCv(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        var originalFilename = file.getOriginalFilename();

        Path destinationFile = this.rootCvLocation.resolve(
                        Paths.get(Objects.requireNonNull(originalFilename)))
                .normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(this.rootCvLocation.toAbsolutePath())) {

            throw new StorageException(
                    "Cannot store file outside current directory.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return originalFilename;
    }

    @Override
    public String storePhoto(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        var originalFilename = file.getOriginalFilename();

        Path destinationFile = this.rootPhotoLocation.resolve(
                        Paths.get(Objects.requireNonNull(originalFilename)))
                .normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(this.rootPhotoLocation.toAbsolutePath())) {

            throw new StorageException(
                    "Cannot store file outside current directory.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return originalFilename;
    }

    @Override
    public Path loadCv(String filename) {
        return this.rootCvLocation.resolve(filename);
    }

    @Override
    public Path loadPhoto(String filename) {
        return this.rootPhotoLocation.resolve(filename);
    }


}
