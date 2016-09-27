package com.upload.model;

public enum UploadStatusEnum {
	EM_ANDAMENTO ("Em andamento."),
    FALHA   	 ("Falha"),
    CONCLUIDO    ("Concluído");
	
	private final String descricao;
	
	UploadStatusEnum(String descricao) {
        this.descricao = descricao;
    }

	public String getDescricao() {
		return descricao;
	}	
}
