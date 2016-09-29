package com.upload.service;

import java.util.List;

import com.upload.model.File;

/**
 * The Interface FileService.
 */
public interface FileService {
	
	/**
	 * Busca um arquivo por id.
	 *
	 * @param id
	 *            the id
	 * @return the file
	 */
	File findById(Long id);

	/**
	 * Salva ou atuliza um arquivo.
	 *
	 * @param file
	 *            the file
	 */
	void saveOrUpdateFile(File file);

	/**
	 * Busca todos os arquivos.
	 *
	 * @return the list
	 */
	List<File> findAllFiles();

	/**
	 * Checa se um arquivo já foi salvo.
	 *
	 * @param file
	 *            the file
	 * @return true, se o arquivo existe
	 */
	public boolean isFileExist(File file);
}
