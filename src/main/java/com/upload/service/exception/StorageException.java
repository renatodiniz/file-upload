package com.upload.service.exception;

/**
 * The Class StorageException.
 * Exceções relacionadas com armazenamento de arquivos.
 */
public class StorageException extends RuntimeException {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 309728306254912289L;

	/**
	 * Instantiates a new storage exception.
	 *
	 * @param message
	 *            the message
	 */
	public StorageException(String message) {
        super(message);
    }

    /**
	 * Instantiates a new storage exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
