package com.upload.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.upload.common.FileChunkUtils;
import com.upload.model.FileModel;
import com.upload.model.dto.UploadFileRequestDTO;
import com.upload.service.exception.StorageException;
import com.upload.service.properties.StorageProperties;

@Service
public class StorageServiceFileSystem implements StorageService {


    private final Path rootLocation;    
    private File directory;

    @Autowired
    public StorageServiceFileSystem(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.directory = null;
    }
    
    @Override
    public void init() {
    	try {
    		// cria o diretório raiz onde serão salvos os arquivos
    		new File(rootLocation.toUri()).mkdir();
    		this.directory = new File(rootLocation.toUri());
    	} catch (IllegalArgumentException e) {
    		e.printStackTrace();
    		throw new StorageException("Ocorreu um erro durante a inicialização do armazenamento.", e);
    	}
    }

    @Override
    public void store(MultipartFile file, UploadFileRequestDTO uploadFileRequest) throws StorageException {
    	File assembledFile = null;
    	File uploadFileDir = null;
    	
    	try {    	
	    	// cria um diretorio com o nome do fileId 
	    	new File(directory, uploadFileRequest.getFileId().toString()).mkdir();
	    	uploadFileDir = new File(directory, uploadFileRequest.getFileId().toString());
	    	
	    	// testa se o arquivo não foi enviado em bloco
	        if (!uploadFileRequest.isChunk()) {
	            assembledFile = new File(uploadFileDir, file.getOriginalFilename());
	            //copia o arquivo para o disco
	            FileCopyUtils.copy(file.getBytes(), assembledFile);
	            uploadFileRequest.setFileAssembled(true);
	            
	        // caso contrário, o arquivo foi enviado em bloco
	        } else {
	            byte[] bytes = file.getBytes();
	            // testa se tamanho do arquivo condiz com o tamanho esperado do bloco
	            if (uploadFileRequest.getChunkFrom() + bytes.length != uploadFileRequest.getChunkTo() + 1)
	                throw new StorageException("Tamanho inesperado de bloco de arquivo: " + bytes.length + 
	                        " != " + (uploadFileRequest.getChunkTo() + 1) + " - " + uploadFileRequest.getChunkFrom());
	            
	            // salva o bloco de arquivo
	            FileChunkUtils.saveChunk(uploadFileDir, file.getOriginalFilename(), uploadFileRequest.getChunkFrom(), bytes);
	            
	            // faz o cálculo da soma em bytes dos blocos de arquivo que já foram salvos no disco
	            TreeMap<Long, Long> chunkStartsToLengths = FileChunkUtils.getChunkStartsToLengths(uploadFileDir, file.getOriginalFilename());
	            long lengthSoFar = FileChunkUtils.getCommonLength(chunkStartsToLengths);                   
	            
	            // se o arquivo inteiro já foi salvo no disco
	            if (lengthSoFar == uploadFileRequest.getFileFullLength()) {
	            	// realiza o parse dos blocos de arquivo que se encontram no disco
	                FileChunkUtils.assembleAndDeleteChunks(uploadFileDir, file.getOriginalFilename(), 
	                        new ArrayList<Long>(chunkStartsToLengths.keySet()));
	                uploadFileRequest.setFileAssembled(true);
	            }
	        }    	         
        } catch (IOException e) {
            throw new StorageException("Ocorreu um erro ao armazenar o arquivo: " + file.getOriginalFilename(), e);
        }
    }    
    
    @Override
    public InputStreamResource download(FileModel fileModel) throws StorageException {
    	InputStreamResource isr;
    	File uploadFileDir = null;
    	File file = null;
    	
		try {
			uploadFileDir = new File(directory, fileModel.getId().toString());
			file = new File(uploadFileDir, fileModel.getName());			
			isr = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new StorageException("Ocorreu um erro ao recuperar o arquivo: " + fileModel.getName(), e);
		}
	    
	    return isr;
    }    
    
}
