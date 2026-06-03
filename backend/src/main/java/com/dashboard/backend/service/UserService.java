package com.dashboard.backend.service;
import com.dashboard.backend.ai.service.AIInsightsService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dashboard.backend.dao.UserDao;
import com.dashboard.backend.dto.ResponseStructure;
import com.dashboard.backend.entity.Role;
import com.dashboard.backend.entity.User;
import com.dashboard.backend.exception.IdNotFoundException;
import com.dashboard.backend.exception.NoRecordAvailableException;



@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AIInsightsService aiInsightsService;
	
	public ResponseEntity<ResponseStructure<User>> saveUser(User user) {
		
		if (user == null) {
            throw new NoRecordAvailableException("User data is null");
        }
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		user.setRole(Role.USER);
		
		User savedUser = userDao.saveUser(user);
		
		aiInsightsService.clearInsightsCache();
		
		ResponseStructure<User> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("User record saved successfully");
		response.setData(savedUser);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<List<User>>> getAllUser() {

	    ResponseStructure<List<User>> response = new ResponseStructure<>();
	    

	    response.setStatusCode(HttpStatus.OK.value()); 
	    response.setMessage("Records Retrieved");
	    response.setData(userDao.getAllUser());

	    return new ResponseEntity<>(response, HttpStatus.OK); 
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteUser(Integer id) {

		User user = userDao.findUserById(id);
	    
	    if (user == null) {
            throw new IdNotFoundException("User not found with ID: " + id);
        }
	    
	    userDao.deleteUserById(id);
	    
	    aiInsightsService.clearInsightsCache();

	    ResponseStructure<String> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setMessage("User deleted successfully");
	    response.setData("Deleted");

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<ResponseStructure<User>> updateUse(Integer id, User user) {

	    User existingUser = userDao.findUserById(id);

	    if (existingUser == null) {
	        throw new RuntimeException("User not found");
	    }

	    // Update only if values exist
	    if (user.getName() != null && !user.getName().isEmpty()) {
	        existingUser.setName(user.getName());
	    }

	    if (user.getEmail() != null && !user.getEmail().isEmpty()) {
	        existingUser.setEmail(user.getEmail());
	    }

	    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
	        existingUser.setPassword(
	            passwordEncoder.encode(user.getPassword()) // 🔐 FIX
	        );
	    }

	    User updatedUser = userDao.saveUser(existingUser);
	    
	    aiInsightsService.clearInsightsCache();

	    ResponseStructure<User> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setMessage("User updated successfully");
	    response.setData(updatedUser);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
