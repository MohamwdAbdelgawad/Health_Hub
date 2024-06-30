package com.patientassistant.home.security.service;

import com.patientassistant.home.security.Repository.UserRepository;
import com.patientassistant.home.security.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public String updatePassword(String userName , String currentPassword , String newPassword){
        Optional<User> user = userRepository.getUserByUsername(userName);
        if(user.isPresent()){
            System.out.println(passwordEncoder.encode(currentPassword));
            System.out.println(user.get().getPassword());
            if(passwordEncoder.matches(currentPassword , user.get().getPassword())){
                user.get().setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user.get());
                return "password updated successfully";
            }
            else return "current password is wrong";
        }
        else return "user not found";
    }
}
