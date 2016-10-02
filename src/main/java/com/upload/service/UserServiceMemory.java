package com.upload.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.upload.model.User;

@Service("userService")
public class UserServiceMemory implements UserService {

	/** Lista que armazena os arquivos. */
	private static List<User> userRecords;
	
	
	static { // inicializa a lista
		userRecords = new ArrayList<User> ();
	}

	public User findById(Long id) {
		for (User user : userRecords) {
			if (user.getId().equals(id)) {
				return user;
			}
		}
		return null;
	}

	public void saveOrUpdateUser(User user) {
		// insere ou atualiza o arquivo na lista
		if (userRecords == null || findById(user.getId()) != null)			
			updateUser(user);
		else
			saveUser(user);
	}

	private void saveUser(User user) {
		userRecords.add(user);
	}

	private void updateUser(User user) {
		int index = userRecords.indexOf(user);
		userRecords.set(index, user);
	}

	public boolean isUserExist(User user) {
		return findById(user.getId()) != null;
	}

}
