package com.upload.service;

import com.upload.model.User;

public interface UserService {

	User findById(Long id);

	void saveOrUpdateUser(User user);

	public boolean isUserExist(User user);
}
