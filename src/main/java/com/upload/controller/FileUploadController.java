package com.upload.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.upload.model.UploadFileRequest;
import com.upload.service.StorageService;

@Controller
public class FileUploadController {
	
	private final StorageService storageService;
	
	@Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

	@PostMapping("/arquivo")
	public ResponseEntity<Map<String, Object>> handleFileUpload(@RequestParam("file") MultipartFile file, 
			@RequestParam("userId") Long userId,
			@RequestParam("name") String name,
			@RequestParam("fileId") Long fileId,
			@RequestHeader("Content-Range") String contentRange) {
		
		UploadFileRequest uploadFileRequest = new UploadFileRequest(fileId, contentRange);
		storageService.store(file, uploadFileRequest);
		
		
		
		
		Map<String, Object> ret = new LinkedHashMap<String, Object>();
//		List<Map<String, Object>> files = new ArrayList<Map<String,Object>>();
//		Map<String, Object> fileInfo = new LinkedHashMap<String, Object>();
//		
//		fileInfo.put("name", file.getName());
//        fileInfo.put("type", file.getContentType());
//        fileInfo.put("size", file.getSize());
//        files.add(fileInfo);
//        
//        ret.put("files", files);
        
        return ResponseEntity
                .ok()
                .body(ret);
	}
	
	private static void saveChunk(File dir, String fileName, 
	        long from, byte[] bytes, long fileFullLength) throws IOException {
	    File target = new File(dir, fileName + "." + from + ".chunk");
	    OutputStream os = new FileOutputStream(target);
	    try {
	        os.write(bytes);
	    } finally {
	        os.close();
	    }
	}

	private static TreeMap<Long, Long> getChunkStartsToLengths(File dir, 
	        String fileName) throws IOException {
	    TreeMap<Long, Long> chunkStartsToLengths = new TreeMap<Long, Long>();
	    for (File f : dir.listFiles()) {
	        String chunkFileName = f.getName();
	        if (chunkFileName.startsWith(fileName + ".") && 
	                chunkFileName.endsWith(".chunk")) {
	            chunkStartsToLengths.put(Long.parseLong(chunkFileName.substring(
	                    fileName.length() + 1, chunkFileName.length() - 6)), f.length());
	        }
	    }
	    return chunkStartsToLengths;
	}

	private static long getCommonLength(TreeMap<Long, Long> chunkStartsToLengths) {
	    long ret = 0;
	    for (long len : chunkStartsToLengths.values())
	        ret += len;
	    return ret;
	}

	private static File assembleAndDeleteChunks(File dir, String fileName, 
	        List<Long> chunkStarts) throws IOException {
	    File assembledFile = new File(dir, fileName);
	    if (assembledFile.exists()) // In case chunks come in concurrent way
	        return assembledFile;
	    OutputStream assembledOs = new FileOutputStream(assembledFile);
	    byte[] buf = new byte[100000];
	    try {
	        for (long chunkFrom : chunkStarts) {
	            File chunkFile = new File(dir, fileName + "." + chunkFrom + ".chunk");
	            InputStream is = new FileInputStream(chunkFile);
	            try {
	                while (true) {
	                    int r = is.read(buf);
	                    if (r == -1)
	                        break;
	                    if (r > 0)
	                        assembledOs.write(buf, 0, r);
	                }
	            } finally {
	                is.close();
	            }
	            chunkFile.delete();
	        }
	    } finally {
	        assembledOs.close();
	    }
	    return assembledFile;
	}

}
