package com.upload.model.dto;

import java.util.regex.Pattern;

import com.upload.service.exception.StorageException;

/**
 * The Class UploadFileRequestDTO.
 */
public class UploadFileRequestDTO {
	
	/** The file id. */
	private Long fileId;
	
	/** The file full length. */
	private Long fileFullLength;
    
    /** The chunk from. */
    private Long chunkFrom;
    
    /** The chunk to. */
    private Long chunkTo;
    
    /** The chunk. */
    private boolean chunk = false;
    
    /** The file assembled. */
    private boolean fileAssembled;
    
	/**
	 * Construtor padrão.
	 *
	 * @param fileId
	 *            the file id
	 * @param contentRange
	 *            content range do header do request.
	 * @throws StorageException
	 *             the storage exception
	 */
	public UploadFileRequestDTO(Long fileId, String contentRange) throws StorageException {
		this.fileFullLength = -1L;
		this.chunkFrom = -1L;
		this.chunkTo = -1L;
		this.fileAssembled = false;
		
	    if (contentRange != null) {
	        if (!contentRange.startsWith("bytes "))
	            throw new StorageException("Formato inesperado de content-range: " + contentRange);
	        
	        String[] fromToAndLength = contentRange.substring(6).split(Pattern.quote("/"));
	        fileFullLength = Long.parseLong(fromToAndLength[1]);
	        String[] fromAndTo = fromToAndLength[0].split(Pattern.quote("-"));
	        chunkFrom = Long.parseLong(fromAndTo[0]);
	        chunkTo = Long.parseLong(fromAndTo[1]);
	        chunk = true;
	    }
	    	    
	    if (fileId != null) {
	    	this.fileId = fileId;
	    } else {
	    	throw new StorageException("Formato inesperado do fileId: fileId não pode ser enviado como null");
	    }
	}
	
	/**
	 * Checa se o arquivo que chegou no request é um bloco de arquivo.
	 *
	 * @return the boolean
	 */
	public Boolean isChunk () {
		return chunk;
	}
	
	/**
	 * Gets the file id.
	 *
	 * @return the file id
	 */
	public Long getFileId() {
		return fileId;
	}

	/**
	 * Sets the file id.
	 *
	 * @param fileId
	 *            the new file id
	 */
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	/**
	 * Gets the file full length.
	 *
	 * @return the file full length
	 */
	public long getFileFullLength() {
		return fileFullLength;
	}

	/**
	 * Sets the file full length.
	 *
	 * @param fileFullLength
	 *            the new file full length
	 */
	public void setFileFullLength(long fileFullLength) {
		this.fileFullLength = fileFullLength;
	}

	/**
	 * Gets the chunk from.
	 *
	 * @return the chunk from
	 */
	public long getChunkFrom() {
		return chunkFrom;
	}

	/**
	 * Sets the chunk from.
	 *
	 * @param chunkFrom
	 *            the new chunk from
	 */
	public void setChunkFrom(long chunkFrom) {
		this.chunkFrom = chunkFrom;
	}

	/**
	 * Gets the chunk to.
	 *
	 * @return the chunk to
	 */
	public long getChunkTo() {
		return chunkTo;
	}

	/**
	 * Sets the chunk to.
	 *
	 * @param chunkTo
	 *            the new chunk to
	 */
	public void setChunkTo(long chunkTo) {
		this.chunkTo = chunkTo;
	}

	/**
	 * Checks if is file assembled.
	 *
	 * @return true, if is file assembled
	 */
	public boolean isFileAssembled() {
		return fileAssembled;
	}

	/**
	 * Sets the file assembled.
	 *
	 * @param fileAssembled
	 *            the new file assembled
	 */
	public void setFileAssembled(boolean fileAssembled) {
		this.fileAssembled = fileAssembled;
	}	
	
}
