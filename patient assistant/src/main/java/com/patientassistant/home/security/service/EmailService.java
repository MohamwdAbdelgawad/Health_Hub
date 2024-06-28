package com.patientassistant.home.security.service;


import com.patientassistant.home.security.entites.User;

public interface EmailService  {

    String sendMail(User user, String token) ;

}
