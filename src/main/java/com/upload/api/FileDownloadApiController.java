package com.upload.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upload.model.FileModel;
import com.upload.service.FileService;
import com.upload.service.StorageService;
import com.upload.service.exception.FileNotFoundException;
import com.upload.service.exception.StorageException;

@RestController
public class FileDownloadApiController implements FileDownloadApi {
	
	private final StorageService storageService;
	private final FileService fileService;
	private static final Logger logger = Logger.getLogger(FileDownloadApiController.class);
	
	@Autowired
    public FileDownloadApiController(StorageService storageService, FileService fileService) {
        this.fileService = fileService;
        this.storageService = storageService;
    }
	
	@RequestMapping(value = "/arquivo/{arquivoId}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> fileDownloadGet(
			@PathVariable Long arquivoId) throws FileNotFoundException{
		
		FileModel fileDownload = fileService.findById(arquivoId);
		
		if (fileDownload == null) {
			throw new FileNotFoundException();
		}			
		
	    HttpHeaders respHeaders = new HttpHeaders();
	    respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    respHeaders.setContentLength(fileDownload.getLength());
	    respHeaders.setContentDispositionFormData("attachment", fileDownload.getName());

	    InputStreamResource isr = null;
		try {
			isr = storageService.download(fileDownload);
		} catch (StorageException e) {
			logger.error("Erro durante o download do arquivo.", e);
			return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
	}
}
