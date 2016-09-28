package com.upload.service;

public class StorageException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 309728306254912289L;

	public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
