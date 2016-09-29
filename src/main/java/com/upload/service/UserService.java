package com.upload.service;

import com.upload.model.User;

/**
 * The Interface UserService.
 */
public interface UserService {
	
	/**
	 * Busca um usuário por id.
	 *
	 * @param id
	 *            the id
	 * @return the user
	 */
	User findById(Long id);

	/**
	 * Salva um usuário.
	 *
	 * @param user
	 *            the user
	 */
	void saveUser(User user);

	/**
	 * Checa se um usuário já foi salvo.
	 *
	 * @param user
	 *            the user
	 * @return true, se o usuário existe
	 */
	public boolean isUserExist(User user);
}
