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

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * The Class FileUploadController.
 */
@RestController
@RequestMapping("/arquivo")
public class FileUploadController {

	private final StorageService storageService;

	private final FileService fileService;

	private final UserService userService;

	private static final Logger logger = Logger.getLogger(FileUploadController.class);
	
	@Autowired
    public FileUploadController(StorageService storageService, FileService fileService, UserService userService) {
        this.storageService = storageService;
        this.fileService = fileService;
        this.userService = userService;
    }

	@ApiOperation(value = "postArquivo", 
			notes = "Método responsável por realizar o upload de um arquivo. "
				+ "Suporta arquivos grandes, enviados em bloco. \n "
				+ "Acho que não é possível simular o envio do arquivo em bloco utilizando o Swagger "
				+ "da mesma maneira que o JQuery File Upload Plugin faz, "
				+ "entretanto caso seja enviado arquivo até 1Mb, ele é capaz de realizar o teste.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "file", value = "Arquivo multipart enviado.", paramType = "form", dataType = "file", required = true),
			@ApiImplicitParam(name = "userId", value = "Id do Usuário.", paramType = "query", dataType = "long", required = true),
			@ApiImplicitParam(name = "name", value = "Nome do Usuário.", paramType = "query", dataType = "string", required = true),
			@ApiImplicitParam(name = "fileId", value = "Id do Arquivo.", paramType = "query", dataType = "long", required = true),
			@ApiImplicitParam(name = "Content-Range", value = "Content-Range. Enviado no Header do Request caso o arquivo seja enviado em bloco", paramType = "header", dataType = "string") })
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
	
	@ApiOperation(value = "getArquivos", notes = "Retorna a listagem de todos os arquivos que foram enviados.")
	@RequestMapping(value = "/arquivos", method = RequestMethod.POST)
	Collection<File> readFiles() {
		return fileService.findAllFiles();
	}

}
