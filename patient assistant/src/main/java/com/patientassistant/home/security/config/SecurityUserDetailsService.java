package com.patientassistant.home.security.config;


import com.patientassistant.home.security.Repository.UserRepository;
import com.patientassistant.home.security.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public SecurityUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.getUserByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User with username " + username + " not found !");
        }
        return optionalUser.get();
    }

}
