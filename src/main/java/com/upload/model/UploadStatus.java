package com.upload.model;

public enum UploadStatus {
	EM_ANDAMENTO ("Em andamento."),
    FALHA   	 ("Falha"),
    CONCLUIDO    ("Concluído");
	
	private final String description;
	
	UploadStatus(String description) {
        this.description = description;
    }

	public String getDescription() {
		return description;
	}	
}
