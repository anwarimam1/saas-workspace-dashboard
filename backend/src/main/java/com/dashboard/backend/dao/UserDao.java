package com.dashboard.backend.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dashboard.backend.entity.User;
import com.dashboard.backend.repository.UserRepository;

@Repository
public class UserDao {
	@Autowired
	private UserRepository userRepository;
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	public void deleteUserById(Integer id) {
	    userRepository.deleteById(id);
	}
	
	public User findUserById(Integer id) {
	    return userRepository.findById(id).orElse(null);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}
}
