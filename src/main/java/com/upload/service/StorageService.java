package com.upload.service;

import org.springframework.web.multipart.MultipartFile;

import com.upload.model.UploadFileRequest;

public interface StorageService {

    void init();
    
    void store(MultipartFile file, UploadFileRequest uploadFileRequest);
    
//    Stream<Path> loadAll();
//
//    Path load(String filename);
//
//    Resource loadAsResource(String filename);
//
//    void deleteAll();

}
