package com.upload.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.upload.model.User;

/**
 * The Class UserServiceImpl.
 */
@Service("userService")
public class UserServiceMemory implements UserService {

	/** Auto Generate ID Number. */
	private static final AtomicLong counter = new AtomicLong();

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
	 * @see com.upload.service.UserService#saveUser(com.upload.model.User)
	 */
	public void saveUser(User user) {
		user.setId(counter.incrementAndGet());
		userRecords.add(user);
	}

	/* (non-Javadoc)
	 * @see com.upload.service.UserService#isUserExist(com.upload.model.User)
	 */
	public boolean isUserExist(User user) {
		return findById(user.getId()) != null;
	}

}
