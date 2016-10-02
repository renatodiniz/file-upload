package com.upload.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("File ")
public class File {

	@ApiModelProperty(value = "Id do arquivo", required = true)
	private Long id;
	
	@ApiModelProperty(value = "Usuario", required = true)
	private User user;
	
	@ApiModelProperty(value = "Inicio do upload em milisegundos", required = true)
	private Long startTime;
	
	@ApiModelProperty(value = "Termino do upload em milisegundos")
	private Long endTime;
	
	@ApiModelProperty(value = "Tempo de duracao do upload formatado mm:ss:SSS")
	private String timeFormatted;
	
	@ApiModelProperty(value = "Nome do arquivo", required = true)
	private String name;
	
	@ApiModelProperty(value = "Status do upload", required = true)
	private UploadStatus uploadStatus;
	
	@ApiModelProperty(value = "Status do upload formatado", required = true)
	private String uploadStatusFormatted;
	
	@ApiModelProperty(value = "Numero de blocos do arquivo", required = true)
	private Long numberOfChunks;

	public File(Long fileId, User user, Long startTime, Long endTime, String name, UploadStatus uploadStatus) {
		super();
		this.id = fileId;
		this.user = user;
		this.startTime = startTime;
		this.endTime = endTime;
		this.name = name;
		this.uploadStatus = uploadStatus;
		this.numberOfChunks = 0L;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		File other = (File) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String getTimeFormatted() {
		if (startTime != null && endTime != null) {
			Date date = new Date(endTime - startTime);
			DateFormat formatter = new SimpleDateFormat("mm:ss:SSS");
			timeFormatted = formatter.format(date);
		}
		return timeFormatted;
	}
	
	public String getUploadStatusFormatted() {
		switch (uploadStatus) {
			case PROCESSING:
				uploadStatusFormatted = "Em andamento";
				break;
			case FAILED:
				uploadStatusFormatted = "Falha";
				break;
			case COMPLETED:
				uploadStatusFormatted = "Concluído";
				break;
			default:
				break;
		}
		return uploadStatusFormatted;
	}
	
	public void incrementNumberOfChunks() {
		this.numberOfChunks++;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UploadStatus getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(UploadStatus uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public Long getNumberOfChunks() {
		return numberOfChunks;
	}

	public void setNumberOfChunks(Long numberOfChunks) {
		this.numberOfChunks = numberOfChunks;
	}	

}
