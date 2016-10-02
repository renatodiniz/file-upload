package com.upload.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import com.upload.model.FileModel;
import com.upload.model.dto.UploadFileRequestDTO;
import com.upload.service.exception.StorageException;

public interface StorageService {


    void init();

    void store(MultipartFile file, UploadFileRequestDTO uploadFileRequestDTO) throws StorageException;
    
    InputStreamResource download(FileModel file) throws StorageException;

}
