
package com.springSecurity.api.BasicSecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springSecurity.api.BasicSecurity.modal.UserModal;
import com.springSecurity.api.BasicSecurity.service.UserService;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody UserModal user  ) {
       return userService.register(user);
       
       
    }

    @GetMapping("/user")
    public List<UserModal> readUsers() {
        return userService.readUsers();
    }
     
    @GetMapping("/user/{id}")
    public UserModal readUser(@PathVariable Long id){
        return userService.readUser(id);
    }
    @PutMapping("/update/{id}")
    public String updateUser(@RequestBody UserModal userModal, @PathVariable long id){
        return userService.updateUser(id, userModal);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id){
        if(userService.deleteUser(id))
        return "Delete succsefully";
        return "Not Found";
    }
}