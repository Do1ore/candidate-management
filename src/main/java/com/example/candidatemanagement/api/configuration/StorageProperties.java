package com.example.candidatemanagement.api.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private final String baseDirectory = "src/main/resources/static/";
    private final String cvLocation = "upload-dir/cv";
    private final String photoLocation = "upload-dir/photo";


    public String getCvLocation() {
        return baseDirectory + cvLocation;
    }
    public String getPhotoLocation() {
        return baseDirectory + photoLocation;
    }



}