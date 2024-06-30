package com.patientassistant.home.security.service;

import com.patientassistant.home.security.Repository.OtpRepository;
import com.patientassistant.home.security.entites.Otp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpService {
    @Autowired
    private OtpRepository otpRepository;

    public void saveOtp(String email, String otp) {
        Otp otpEntity = new Otp();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpirationTime(LocalDateTime.now().plusMinutes(10));
        otpRepository.save(otpEntity);
    }
    public Optional<Otp> findByEmailAndOtp(String email , String otp){
        return otpRepository.findByEmailAndOtp(email , otp);
    }
}
