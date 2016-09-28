package com.upload.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.upload.model.UploadFileRequest;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private File directory;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.directory = null;
    }

    @Override
    public void store(MultipartFile file, UploadFileRequest uploadFileRequest) {
//        try {
//            
//        } catch (IOException e) {
//            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
//        }
    }

    @Override
    public void init() {
        try {
        	new File(rootLocation.toUri()).mkdir();
            this.directory = new File(rootLocation.toUri());
        } catch (IllegalArgumentException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
