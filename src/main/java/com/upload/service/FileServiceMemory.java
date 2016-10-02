package com.upload.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.upload.model.File;

@Service("fileService")
public class FileServiceMemory implements FileService {

	/** Lista que armazena os arquivos. */
	private static List<File> fileRecords;
	
	
	static { // inicializa a lista
		fileRecords = new ArrayList<File> ();
	}

	public File findById(Long id) {
		for (File file : fileRecords) {
			if (file.getId().equals(id)) {
				return file;
			}
		}
		return null;
	}

	public void saveOrUpdateFile(File file) {
		// insere ou atualiza o arquivo na lista
		if (fileRecords == null || findById(file.getId()) != null)			
			updateFile(file);
		else
			saveFile(file);
	}

	private void saveFile(File file) {
		fileRecords.add(file);
	}

	private void updateFile(File file) {
		int index = fileRecords.indexOf(file);
		fileRecords.set(index, file);
	}

	public List<File> findAllFiles() {
		return fileRecords;
	}

	public boolean isFileExist(File file) {
		return findById(file.getId()) != null;
	}

}
