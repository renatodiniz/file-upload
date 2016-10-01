package com.upload.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.upload.model.File;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface FileListApi {

	@ApiOperation(value = "Lista os arquivos enviados.", response = File.class, responseContainer = "List", tags = { "FileListApi", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Upload realizado com sucesso.", response = File.class, responseContainer = "List") })
	@RequestMapping(value = "/arquivo/arquivos", method = RequestMethod.GET)
	public ResponseEntity<List<File>> fileUploadPost( );

}
