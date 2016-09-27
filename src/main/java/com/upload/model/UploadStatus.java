package com.upload.model;

public enum UploadStatus {
	EM_ANDAMENTO ("Em andamento."),
    FALHA   	 ("Falha"),
    CONCLUIDO    ("Concluído");
	
	private final String descricao;
	
	UploadStatus(String descricao) {
        this.descricao = descricao;
    }

	public String getDescricao() {
		return descricao;
	}	
}
