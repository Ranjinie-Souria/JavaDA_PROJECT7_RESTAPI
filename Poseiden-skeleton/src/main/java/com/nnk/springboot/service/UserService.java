package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	public Optional<User> getUserById(Integer id) {
		return userRepository.findById(id);
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);		
	}
	
	public void deleteUserById(Integer id) {
		userRepository.deleteById(id);
	}

}
