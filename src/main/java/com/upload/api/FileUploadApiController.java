package com.upload.api;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.upload.model.FileModel;
import com.upload.model.UploadStatus;
import com.upload.model.User;
import com.upload.model.dto.UploadFileRequestDTO;
import com.upload.service.FileService;
import com.upload.service.StorageService;
import com.upload.service.UserService;
import com.upload.service.exception.StorageException;

@RestController
public class FileUploadApiController implements FileUploadApi {

	private final StorageService storageService;
	private final FileService fileService;
	private final UserService userService;
	private static final Logger logger = Logger.getLogger(FileUploadApiController.class);
	
	@Autowired
    public FileUploadApiController(StorageService storageService, FileService fileService, UserService userService) {
        this.storageService = storageService;
        this.fileService = fileService;
        this.userService = userService;
    }

	public ResponseEntity<FileModel> fileUploadPost(
			@RequestParam("file") MultipartFile multipartFile, 
			@RequestParam("userId") Long userId,
			@RequestParam("name") String name,
			@RequestParam("fileId") Long fileId,
			@RequestHeader(required = false, value = "Content-Range") String contentRange) {
		
		User user = userService.findById(userId);
		FileModel file = fileService.findById(fileId);
				
		if (user == null)
			user = new User(userId, name);				
		
		if (file == null)
			file = new FileModel(fileId, user, Calendar.getInstance().getTimeInMillis(), null, multipartFile.getOriginalFilename(), UploadStatus.PROCESSING);
		
		try {			
			// cria DTO com dados do request e realiza armazenamento do arquivo
			UploadFileRequestDTO uploadFileRequest = new UploadFileRequestDTO(fileId, contentRange);
			storageService.store(multipartFile, uploadFileRequest);
			file.incrementNumberOfChunks();
			
			// testa se o upload do arquivo foi finalizado
			if (uploadFileRequest.isFileAssembled()) {
				file.setUploadStatus(UploadStatus.COMPLETED);
				file.setEndTime(Calendar.getInstance().getTimeInMillis());
				file.setLength(uploadFileRequest.getFileFullLength());
			}
			
			// cria/atualiza usuário e arquivo
			userService.saveOrUpdateUser(user);
			fileService.saveOrUpdateFile(file);
			
		} catch (StorageException e) {
			// caso ocorra algum erro no processo, seta o status do arquivo como falha
			file.setUploadStatus(UploadStatus.FAILED);
			fileService.saveOrUpdateFile(file);
			logger.error("Erro durante o armazenamento do arquivo.", e);
			
			return new ResponseEntity<FileModel>(HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		return new ResponseEntity<FileModel>(file, HttpStatus.OK);
	}
}
