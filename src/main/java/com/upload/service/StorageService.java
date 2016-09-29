package com.upload.service;

import org.springframework.web.multipart.MultipartFile;

import com.upload.model.dto.UploadFileRequestDTO;

/**
 * The Interface StorageService.
 */
public interface StorageService {

    /**
	 * Inicializa o serviço de armazenamento.
	 */
    void init();
    
    /**
	 * Método responsável por realizar o armazenamento do arquivo.
	 *
	 * @param file
	 *            the file
	 * @param uploadFileRequest
	 *            the upload file request
	 */
    void store(MultipartFile file, UploadFileRequestDTO uploadFileRequestDTO);

}
