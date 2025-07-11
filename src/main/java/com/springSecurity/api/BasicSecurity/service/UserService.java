
package com.springSecurity.api.BasicSecurity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springSecurity.api.BasicSecurity.entity.User;
import com.springSecurity.api.BasicSecurity.modal.UserModal;
import com.springSecurity.api.BasicSecurity.repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepo userRepo, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }
     
    @Transactional
    public String register(UserModal userModal) {
        if (userModal == null) {
            throw new IllegalArgumentException("User data cannot be null");
        }
        if (userModal.getUsername() == null || userModal.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (userModal.getPassword() == null || userModal.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (userRepo.findByUsername(userModal.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User userEntity = new User();
        BeanUtils.copyProperties(userModal, userEntity);
        userEntity.setPassword(encoder.encode(userModal.getPassword())); // Encode password
        userRepo.save(userEntity);
        return "User registered successfully";
    }
    /* 
    public String  register(String username, String password, String email, String role) {
        // Check if username already exists
        if (userRepo.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        // Validate password is not null or empty
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password)); // Use password instead of rawPassword
        user.setEmail(email);
        user.setRole(role);
        userRepo.save(user);
        return "Register Succsesfully";
    }
        */

    public UserModal readUser(long id){
        User user = userRepo.findById(id).get();
        UserModal userModal = new UserModal();
        BeanUtils.copyProperties(user,userModal);
        return userModal;
    }
   

    public List<UserModal> readUsers() {
        List<User> users = userRepo.findAll();
        List<UserModal> userModals = new ArrayList<>();
        for (User user : users) {
            UserModal userModal = new UserModal();
            userModal.setId(user.getId());
            userModal.setUsername(user.getUsername());
            userModal.setEmail(user.getEmail());
            userModal.setRole(user.getRole());
            userModals.add(userModal);
        }
        return userModals;
    }

    public boolean deleteUser(long id){
        User user = userRepo.findById(id).get();
        userRepo.delete(user);
        return true;
    }

	public String updateUser(Long id, UserModal userModal) {
		
		User exestingUser = userRepo.findById(id).get();
		
		 exestingUser.setUsername(userModal.getUsername());
         exestingUser.setPassword(userModal.getPassword());
         exestingUser.setEmail(userModal.getEmail());
         exestingUser.setRole(userModal.getRole());
		 
		userRepo.save( exestingUser);
		
		return "Update successfully";
	}

   
}
