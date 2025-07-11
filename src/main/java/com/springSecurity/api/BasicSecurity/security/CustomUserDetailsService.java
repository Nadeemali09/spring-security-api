package com.springSecurity.api.BasicSecurity.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springSecurity.api.BasicSecurity.entity.User;
import com.springSecurity.api.BasicSecurity.repository.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    public CustomUserDetailsService(UserRepo userRepo){
           this.userRepo=userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not Found Exception"));
        return new CustomUserDetails(user);
    
    
    }
    
}
