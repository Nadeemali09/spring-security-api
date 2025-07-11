package com.springSecurity.api.BasicSecurity.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springSecurity.api.BasicSecurity.entity.User;
@Repository
public interface UserRepo extends JpaRepository<User,Long>{

   
 

     Optional<User> findByEmail(String email);

     Optional<User> findByUsername(String username);
    

    
}
