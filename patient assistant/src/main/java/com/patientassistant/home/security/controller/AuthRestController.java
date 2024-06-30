package com.patientassistant.home.security.controller;
import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.services.DoctorService;
import com.patientassistant.home.patient.entity.Patient;
import com.patientassistant.home.patient.services.PatientService;
import com.patientassistant.home.security.dto.AuthenticationReq;
import com.patientassistant.home.security.dto.LoginRequest;
import com.patientassistant.home.security.dto.ResetPasswordRequest;
import com.patientassistant.home.security.entites.Otp;
import com.patientassistant.home.security.entites.User;
import com.patientassistant.home.security.service.AuthService;
import com.patientassistant.home.security.utils.OtpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Log
public class AuthRestController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register/patient")
    public ResponseEntity<?> registerPatient(@RequestBody Patient request) {
        return authService.registerPatient(request);
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<?> registerDoctor(@RequestBody Doctor request) {
        return authService.registerDoctor(request);
    }

    @GetMapping("/confirm")
    public ResponseEntity confirmAccount(@Valid @RequestParam("token") String token) {
        return authService.confirmAccount(token);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest authenticationReq) {
        log.info("===> Authentication Req");
        return authService.login(authenticationReq);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return authService.refreshToken(request, response);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        System.out.println(email);
        return authService.forgotPassword(email);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
       return authService.resetPassword(request.getEmail() , request.getOtp() , request.getNewPassword());
        }
    }

