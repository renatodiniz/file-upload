package com.upload.model;

/**
 * The Enum UploadStatus.
 */
public enum UploadStatus {
	
	/** Upload em andamento. */
	EM_ANDAMENTO ("Em andamento."),
    
    /** Upload com falha. */
    FALHA   	 ("Falha"),
    
    /** Upload concluído. */
    CONCLUIDO    ("Concluído");
	
	/** Descrição do status. */
	private final String description;
	
	/**
	 * Construtor.
	 *
	 * @param description
	 *            the description
	 */
	UploadStatus(String description) {
        this.description = description;
    }

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}	
}
