package com.patientassistant.home.security.service;


import com.patientassistant.home.security.Repository.ConfirmationTokenRepository;
import com.patientassistant.home.security.entites.ConfirmationToken;
import com.patientassistant.home.security.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenServiceImp implements ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenServiceImp(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public ConfirmationToken saveConfirmationToken(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user);
        confirmationTokenRepository.save(confirmationToken);
        return confirmationToken;
    }

    @Override
    public ConfirmationToken getConfirmationToken(String token) {
        return confirmationTokenRepository.getByToken(token).orElse(null);
    }

    @Override
    public int confirmToken(String token) {
        return confirmationTokenRepository.confirmToken(token, LocalDateTime.now());
    }

}
