package com.upload.model;

/**
 * The Enum UploadStatus.
 */
public enum UploadStatus {
	
	/** Upload em andamento. */
	PROCESSING ("Em andamento."),
    
    /** Upload com falha. */
    FAILED   	 ("Falhou"),
    
    /** Upload concluído. */
    COMPLETED    ("Concluído");
	
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
