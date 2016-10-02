package com.upload.service;

import java.util.List;

import com.upload.model.FileModel;

public interface FileService {
	

	FileModel findById(Long id);

	void saveOrUpdateFile(FileModel file);

	List<FileModel> findAllFiles();

	public boolean isFileExist(FileModel file);
}
