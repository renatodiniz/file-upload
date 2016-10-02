package com.upload.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.upload.model.FileModel;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface FileUploadApi {

	@ApiOperation(value = "Realiza upload de arquivo. Suporta arquivo enviado em blocos.", response = FileModel.class, tags = { "FileUploadApi", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Upload realizado com sucesso.", response = FileModel.class),
			@ApiResponse(code = 500, message = "Erro inesperado.", response = FileModel.class) })
	@RequestMapping(value = "/arquivo", method = RequestMethod.POST)
	public ResponseEntity<FileModel> fileUploadPost(
			
		@ApiParam(value = "Arquivo multipart enviado.") @RequestParam("file") MultipartFile multipartFile, 
		@ApiParam(value = "Id do Usuário.") @RequestParam("userId") Long userId,
		@ApiParam(value = "Nome do Usuário.") @RequestParam("name") String name,
		@ApiParam(value = "Id do Arquivo.") @RequestParam("fileId") Long fileId,
		@ApiParam(value = "Content-Range.") @RequestHeader(required = false, value = "Content-Range") String contentRange
	);

}
