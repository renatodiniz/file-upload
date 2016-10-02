package com.upload.service;

import org.springframework.web.multipart.MultipartFile;

import com.upload.model.dto.UploadFileRequestDTO;

public interface StorageService {


    void init();

    void store(MultipartFile file, UploadFileRequestDTO uploadFileRequestDTO);

}
