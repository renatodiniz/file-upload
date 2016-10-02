package com.upload.service;

import java.util.List;

import com.upload.model.File;

public interface FileService {
	

	File findById(Long id);

	void saveOrUpdateFile(File file);

	List<File> findAllFiles();

	public boolean isFileExist(File file);
}
