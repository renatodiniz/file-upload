package com.upload.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.upload.model.File;
import com.upload.model.UploadStatus;
import com.upload.model.User;
import com.upload.model.dto.UploadFileRequestDTO;
import com.upload.service.FileService;
import com.upload.service.StorageService;
import com.upload.service.UserService;
import com.upload.service.exception.StorageException;


/**
 * The Class FileUploadController.
 */
@RestController
@RequestMapping("/arquivo")
public class FileUploadController {
	
	/** Serviço de armazenamento de arquivos. */
	private final StorageService storageService;
	
	/** Serviço de persistência de arquivo. */
	private final FileService fileService;
	
	/** Serviço de persistência de usuário. */
	private final UserService userService;
	
	/** Log4j logger. */
	private static final Logger logger = Logger.getLogger(FileUploadController.class);
	
	/**
	 * Instantiates a new file upload controller.
	 *
	 * @param storageService
	 *            the storage service
	 */
	@Autowired
    public FileUploadController(StorageService storageService, FileService fileService, UserService userService) {
        this.storageService = storageService;
        this.fileService = fileService;
        this.userService = userService;
    }

	/**
	 * Método responsável por realizar o upload de um arquivo.
	 * Capaz de suportar upload de arquivos grandes enviados em blocos.
	 * Faz uso do Content-Range header que é transmitido pelo plugin jQuery-File-Upload 
	 * para cada bloco de arquivo.
	 *
	 * @param multipartFile
	 *            arquivo multipart
	 * @param userId
	 *            id do usuário
	 * @param name
	 *            nome do usuário
	 * @param fileId
	 *            id único do arquivo
	 * @param contentRange
	 *            Content-Range header
	 * @return the response entity
	 * 			  Entidade de resposta com informações relevantes sobre o upload.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> handleFileUpload(@RequestParam("file") MultipartFile multipartFile, 
			@RequestParam("userId") Long userId,
			@RequestParam("name") String name,
			@RequestParam("fileId") Long fileId,
			@RequestHeader(required = false, value = "Content-Range") String contentRange) {
		
		User user = userService.findById(userId);
		File file = fileService.findById(fileId);
		Map<String, Object> ret = new LinkedHashMap<String, Object>();
				
		if (user == null)
			user = new User(userId, name);				
		
		if (file == null)
			file = new File(fileId, user, Calendar.getInstance(), null, multipartFile.getOriginalFilename(), UploadStatus.PROCESSING);
		
		try {			
			// cria objeto com dados do request e realiza armazenamento do arquivo
			UploadFileRequestDTO uploadFileRequest = new UploadFileRequestDTO(fileId, contentRange);
			storageService.store(multipartFile, uploadFileRequest);
			file.incrementNumberOfChunks();
			
			// testa se o upload do arquivo foi finalizado
			if (uploadFileRequest.isFileAssembled()) {
				// criando messagem de retorno da requisição
				String message = "Upload do arquivo %s realizado com sucesso: %s";
				String currentDateAndTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
				ret.put("message", String.format(message, multipartFile.getOriginalFilename(), currentDateAndTime));
				
				
				file.setUploadStatus(UploadStatus.COMPLETED);
				file.setEndTime(Calendar.getInstance());
			}
			
			// cria/atualiza usuário e arquivo
			userService.saveOrUpdateUser(user);
			fileService.saveOrUpdateFile(file);
			
		} catch (StorageException e) {
			// caso ocorra algum erro no processo, seta o status do arquivo como falha
			file.setUploadStatus(UploadStatus.FAILED);
			fileService.saveOrUpdateFile(file);

			logger.error("Erro durante o armazenamento do arquivo.", e);
			ret.put("message", e.getMessage());
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ret);			
		}
		return ResponseEntity.ok().body(ret);
	}
	
	@RequestMapping("/arquivos")
	Collection<File> readFiles() {
		return fileService.findAllFiles();
	}

}
