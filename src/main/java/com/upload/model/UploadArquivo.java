package com.upload.model;

import java.util.Calendar;

public class UploadArquivo {
	
	/*
	 * Campos retornados: 
		- Identificador do usuário que enviou o arquivo
		- Nome do arquivo
		- Status do upload (Em andamento, falha ou concluído)
		- Tempo de envio
		- Quantidade de blocos em que o arquivo foi dividido
		- Link para download do arquivo
	 */

	private Long id;
	private Calendar inicioEnvio;
	private Calendar terminoEnvio;
	private String nomeArquivo;
	private UploadStatusEnum uploadStatus; 
	private Long quantidadeChunks;	
	
	public UploadArquivo() {
		id = (long) 0;
	}

	public UploadArquivo(Long id, Calendar inicioEnvio, Calendar terminoEnvio, String nomeArquivo,
			UploadStatusEnum uploadStatus, Long quantidadeChunks) {
		super();
		this.id = id;
		this.inicioEnvio = inicioEnvio;
		this.terminoEnvio = terminoEnvio;
		this.nomeArquivo = nomeArquivo;
		this.uploadStatus = uploadStatus;
		this.quantidadeChunks = quantidadeChunks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomeArquivo == null) ? 0 : nomeArquivo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UploadArquivo other = (UploadArquivo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomeArquivo == null) {
			if (other.nomeArquivo != null)
				return false;
		} else if (!nomeArquivo.equals(other.nomeArquivo))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getInicioEnvio() {
		return inicioEnvio;
	}

	public void setInicioEnvio(Calendar inicioEnvio) {
		this.inicioEnvio = inicioEnvio;
	}

	public Calendar getTerminoEnvio() {
		return terminoEnvio;
	}

	public void setTerminoEnvio(Calendar terminoEnvio) {
		this.terminoEnvio = terminoEnvio;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public UploadStatusEnum getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(UploadStatusEnum uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public Long getQuantidadeChunks() {
		return quantidadeChunks;
	}

	public void setQuantidadeChunks(Long quantidadeChunks) {
		this.quantidadeChunks = quantidadeChunks;
	}	
}
