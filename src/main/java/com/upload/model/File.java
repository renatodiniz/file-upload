package com.upload.model;

import java.util.Calendar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * The Class File.
 */
@ApiModel(description = "Esse objeto representa um Arquivo.")
public class File {

	/** The id. */
	private Long id;
	
	/** The user. */
	private User user;
	
	/** The start time. */
	private Calendar startTime;
	
	/** The end time. */
	private Calendar endTime;
	
	/** The name. */
	private String name;
	
	/** The upload status. */
	private UploadStatus uploadStatus;
	
	/** The number of chunks. */
	private Long numberOfChunks;

	/**
	 * Instantiates a new file.
	 *
	 * @param user
	 *            the user
	 * @param startTime
	 *            the start time
	 * @param endTime
	 *            the end time
	 * @param name
	 *            the name
	 * @param uploadStatus
	 *            the upload status
	 */
	public File(Long fileId, User user, Calendar startTime, Calendar endTime, String name, UploadStatus uploadStatus) {
		super();
		this.id = fileId;
		this.user = user;
		this.startTime = startTime;
		this.endTime = endTime;
		this.name = name;
		this.uploadStatus = uploadStatus;
		this.numberOfChunks = 0L;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
	
	/**
	 * Incrementa o número de blocos de arquivo.
	 */
	public void incrementNumberOfChunks() {
		this.numberOfChunks++;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@ApiModelProperty(notes = "Id do Arquivo.", required = true)
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	@ApiModelProperty(notes = "Usuário que enviou o arquivo.", required = true)
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	@ApiModelProperty(notes = "Hora de início do upload.", required = true)
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time.
	 *
	 * @param startTime
	 *            the new start time
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the end time.
	 *
	 * @return the end time
	 */
	@ApiModelProperty(notes = "Hora de término do upload.", required = true)
	public Calendar getEndTime() {
		return endTime;
	}

	/**
	 * Sets the end time.
	 *
	 * @param endTime
	 *            the new end time
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@ApiModelProperty(notes = "Nome do Arquivo.", required = true)
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the upload status.
	 *
	 * @return the upload status
	 */
	@ApiModelProperty(notes = "Status do upload.", required = true)
	public UploadStatus getUploadStatus() {
		return uploadStatus;
	}

	/**
	 * Sets the upload status.
	 *
	 * @param uploadStatus
	 *            the new upload status
	 */
	public void setUploadStatus(UploadStatus uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	/**
	 * Gets the number of chunks.
	 *
	 * @return the number of chunks
	 */
	@ApiModelProperty(notes = "Quantidade de blocos em que o arquivo foi dividido.", required = true)
	public Long getNumberOfChunks() {
		return numberOfChunks;
	}

	/**
	 * Sets the number of chunks.
	 *
	 * @param numberOfChunks
	 *            the new number of chunks
	 */
	public void setNumberOfChunks(Long numberOfChunks) {
		this.numberOfChunks = numberOfChunks;
	}	

}
