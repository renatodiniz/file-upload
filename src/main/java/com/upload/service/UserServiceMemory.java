package com.upload.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.upload.model.User;

/**
 * The Class UserServiceImpl.
 */
@Service("userService")
public class UserServiceMemory implements UserService {

	/** Lista que armazena os arquivos. */
	private static List<User> userRecords;
	
	// Inicializa a lista
	static {
		userRecords = new ArrayList<User> ();
	}

	/* (non-Javadoc)
	 * @see com.upload.service.UserService#findById(java.lang.Long)
	 */
	public User findById(Long id) {
		for (User user : userRecords) {
			if (user.getId().equals(id)) {
				return user;
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.upload.service.UserService#saveOrUpdateUser(com.upload.model.User)
	 */
	public void saveOrUpdateUser(User user) {
		// insere ou atualiza o arquivo na lista
		if (userRecords == null || findById(user.getId()) != null)			
			updateUser(user);
		else
			saveUser(user);
	}

	/**
	 * Salva um usuario.
	 *
	 * @param user
	 *            the user
	 */
	private void saveUser(User user) {
		userRecords.add(user);
	}
	
	/**
	 * Atualiza um usuario.
	 *
	 * @param user
	 *            the user
	 */
	private void updateUser(User user) {
		int index = userRecords.indexOf(user);
		userRecords.set(index, user);
	}

	/* (non-Javadoc)
	 * @see com.upload.service.UserService#isUserExist(com.upload.model.User)
	 */
	public boolean isUserExist(User user) {
		return findById(user.getId()) != null;
	}

}
