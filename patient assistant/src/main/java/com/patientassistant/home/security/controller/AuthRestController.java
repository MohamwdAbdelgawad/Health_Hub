package com.patientassistant.home.security.controller;
import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.services.DoctorService;
import com.patientassistant.home.patient.entity.Patient;
import com.patientassistant.home.patient.services.PatientService;
import com.patientassistant.home.security.dto.*;
import com.patientassistant.home.security.service.AuthService;
import com.patientassistant.home.security.service.UserService;
import com.patientassistant.home.security.utils.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserService userService;

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
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return authService.forgotPassword(forgotPasswordRequest.getEmail());
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
       return authService.resetPassword(request.getEmail() , request.getOtp() , request.getNewPassword());
        }
    @PostMapping("/update-password")
    public String updatePassword(@RequestHeader("Authentication") String token ,
                                 @RequestBody UpdatePasswordRequest updatePasswordRequest){
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String username = jwtTokenUtils.extractUsername(token);
        return userService.updatePassword(username , updatePasswordRequest.getOldPassword()
                , updatePasswordRequest.getNewPassword());
    }
    }

