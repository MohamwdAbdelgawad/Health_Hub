package com.patientassistant.home.security.service;


import com.patientassistant.home.security.entites.ConfirmationToken;
import com.patientassistant.home.security.entites.User;

public interface ConfirmationTokenService {

    ConfirmationToken saveConfirmationToken(User user);

    ConfirmationToken getConfirmationToken(String token) ;

    int confirmToken(String token);

}
