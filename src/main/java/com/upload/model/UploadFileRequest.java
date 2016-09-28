package com.upload.model;

import java.util.regex.Pattern;

import com.upload.service.StorageException;

public class UploadFileRequest {
	
	private Long fileId;
	private Long fileFullLength;
    private Long chunkFrom;
    private Long chunkTo;
    private String fileName;
    
	public UploadFileRequest(Long fileId, String contentRange, String contentDisposition) throws StorageException {
		this.fileFullLength = -1L;
		this.chunkFrom = -1L;
		this.chunkTo = -1L;
		
	    if (contentRange != null) {
	        if (!contentRange.startsWith("bytes "))
	            throw new StorageException("Unexpected range format: " + contentRange);
	        
	        String[] fromToAndLength = contentRange.substring(6).split(Pattern.quote("/"));
	        fileFullLength = Long.parseLong(fromToAndLength[1]);
	        String[] fromAndTo = fromToAndLength[0].split(Pattern.quote("-"));
	        chunkFrom = Long.parseLong(fromAndTo[0]);
	        chunkTo = Long.parseLong(fromAndTo[1]);
	    }
	    
	    this.fileId = fileId;
	}
	
	public Boolean isChunk () {
		if (fileFullLength != null && fileFullLength < 0) {
			return false;
		}
		return true;
	}

	public long getFileFullLength() {
		return fileFullLength;
	}

	public void setFileFullLength(long fileFullLength) {
		this.fileFullLength = fileFullLength;
	}

	public long getChunkFrom() {
		return chunkFrom;
	}

	public void setChunkFrom(long chunkFrom) {
		this.chunkFrom = chunkFrom;
	}

	public long getChunkTo() {
		return chunkTo;
	}

	public void setChunkTo(long chunkTo) {
		this.chunkTo = chunkTo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}	
}
