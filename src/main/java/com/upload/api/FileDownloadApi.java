package com.upload.api;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.upload.service.exception.FileNotFoundException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface FileDownloadApi {
	
	@ApiOperation(value = "Realiza download de um arquivo.", tags = { "FileDownloadApi", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Download realizado com sucesso.") })
	@RequestMapping(value = "/arquivo/{arquivoId}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> fileDownloadGet(
			@ApiParam(value = "Id do arquivo que se deseja fazer download.") @PathVariable Long arquivoId) throws FileNotFoundException;

}
