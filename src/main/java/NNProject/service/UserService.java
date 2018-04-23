package NNProject.service;

import NNProject.model.User;


public interface UserService {
	public User findUserByName(String username);
	public void saveUser(User user);
}
