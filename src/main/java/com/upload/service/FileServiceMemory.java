package com.upload.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.upload.model.FileModel;

@Service("fileService")
public class FileServiceMemory implements FileService {

	/** Lista que armazena os arquivos. */
	private static List<FileModel> fileRecords;
	
	
	static { // inicializa a lista
		fileRecords = new ArrayList<FileModel> ();
	}

	public FileModel findById(Long id) {
		for (FileModel file : fileRecords) {
			if (file.getId().equals(id)) {
				return file;
			}
		}
		return null;
	}

	public void saveOrUpdateFile(FileModel file) {
		// insere ou atualiza o arquivo na lista
		if (fileRecords == null || findById(file.getId()) != null)			
			updateFile(file);
		else
			saveFile(file);
	}

	private void saveFile(FileModel file) {
		fileRecords.add(file);
	}

	private void updateFile(FileModel file) {
		int index = fileRecords.indexOf(file);
		fileRecords.set(index, file);
	}

	public List<FileModel> findAllFiles() {
		return fileRecords;
	}

	public boolean isFileExist(FileModel file) {
		return findById(file.getId()) != null;
	}

}
