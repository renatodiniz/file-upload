package com.upload.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.upload.model.File;

/**
 * The Class FileServiceImpl.
 */
@Service("fileService")
public class FileServiceMemory implements FileService {

	/** Lista que armazena os arquivos. */
	private static List<File> fileRecords;
	
	// Inicializa a lista
	static {
		fileRecords = new ArrayList<File> ();
	}

	/* (non-Javadoc)
	 * @see com.upload.service.FileService#findById(java.lang.Long)
	 */
	public File findById(Long id) {
		for (File file : fileRecords) {
			if (file.getId().equals(id)) {
				return file;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.upload.service.FileService#saveOrUpdateFile(com.upload.model.File)
	 */
	public void saveOrUpdateFile(File file) {
		// insere ou atualiza o arquivo na lista
		if (fileRecords == null || findById(file.getId()) != null)			
			updateFile(file);
		else
			saveFile(file);
	}
	
	
	/**
	 * Salva um arquivo.
	 *
	 * @param file
	 *            the file
	 */
	private void saveFile(File file) {
		fileRecords.add(file);
	}

	/**
	 * Atualiza um arquivo.
	 *
	 * @param file
	 *            the file
	 */
	private void updateFile(File file) {
		int index = fileRecords.indexOf(file);
		fileRecords.set(index, file);
	}

	/* (non-Javadoc)
	 * @see com.upload.service.FileService#findAllFiles()
	 */
	public List<File> findAllFiles() {
		return fileRecords;
	}

	/* (non-Javadoc)
	 * @see com.upload.service.FileService#isFileExist(com.upload.model.File)
	 */
	public boolean isFileExist(File file) {
		return findById(file.getId()) != null;
	}

}
