package com.upload.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "O arquivo não foi encontrado.")
public class FileNotFoundException extends Exception {

	private static final long serialVersionUID = 2850111284611057506L;
	
}
