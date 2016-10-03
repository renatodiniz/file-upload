package com.upload.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.upload.model.FileModel;
import com.upload.service.FileService;

@RestController
public class FileListApiController implements FileListApi {

	private final FileService fileService;
	
	@Autowired
    public FileListApiController(FileService fileService) {
        this.fileService = fileService;
    }

	public ResponseEntity<List<FileModel>> fileUploadPost( ) {		
		List<FileModel> listFiles = fileService.findAllFiles();
		
		if (listFiles.isEmpty()) {
			return new ResponseEntity<List<FileModel>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<FileModel>>(fileService.findAllFiles(), HttpStatus.OK);
	}
}
