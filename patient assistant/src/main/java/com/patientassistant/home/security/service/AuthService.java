package com.patientassistant.home.security.service;

import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.services.DoctorService;
import com.patientassistant.home.patient.entity.Patient;
import com.patientassistant.home.patient.services.PatientService;
import com.patientassistant.home.security.Repository.UserRepository;
import com.patientassistant.home.security.config.AuthenticationProviderService;
import com.patientassistant.home.security.dto.FailureResponseHandler;
import com.patientassistant.home.security.dto.LoginRequest;
import com.patientassistant.home.security.entites.ConfirmationToken;
import com.patientassistant.home.security.entites.Otp;
import com.patientassistant.home.security.entites.User;
import com.patientassistant.home.security.utils.JwtTokenUtils;

import com.patientassistant.home.security.utils.OtpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.patientassistant.home.security.dto.SuccessLoggedResponse.SuccessRegisterResponse;
import static com.patientassistant.home.security.dto.SuccessLoggedResponse.getSuccessfulResponse;
import static com.patientassistant.home.security.utils.AppConstants.EMAIL_CONFORMATION_PREFIX;

@Service
@Log
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private ConfirmationTokenServiceImp confirmationTokenServiceImp;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationProviderService authenticationProviderService;
    @Autowired
    private EmailServiceImp emailServiceImp;


    public ResponseEntity<?> registerDoctor(Doctor doctorInput) {
        doctorInput.setEnabled(true);
        doctorInput.setLocked(false);
        Optional<Doctor> doctor = doctorService.getDoctorByUsername(doctorInput.getUsername());
        if (doctor.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(FailureResponseHandler.builder()
                            .error(doctorInput.getUsername() + " is Exist")
                            .build());
        }
        Doctor savedDoctor ;
        savedDoctor = doctorService.addDoctor(doctorInput);
        if (savedDoctor != null) {
           // sendEmail(savedDoctor);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(SuccessRegisterResponse(savedDoctor, "Given user details are successfully registered"));
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(FailureResponseHandler.builder()
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .build());
    }

    public ResponseEntity<?> registerPatient(Patient patientInput) {
        patientInput.setEnabled(true);
        patientInput.setLocked(false);
        Optional<Patient> patient = patientService.getDoctorByUsername(patientInput.getUsername());
        if (patient.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(FailureResponseHandler.builder()
                            .error(patientInput.getUsername() + " is Exist")
                            .build());
        }
        Patient savedPatient ;
        savedPatient = patientService.createPatient(patientInput);
        if (savedPatient != null) {
           // sendEmail(savedPatient);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(SuccessRegisterResponse(savedPatient, "Given user details are successfully registered"));
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(FailureResponseHandler.builder()
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .build());
    }

    public ResponseEntity<?> confirmAccount(String token) {
        ConfirmationToken confirmationToken = confirmationTokenServiceImp.getConfirmationToken(token);
        if (confirmationToken == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(FailureResponseHandler.builder()
                            .error("Token value is not Valid")
                            .build()
                    );
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (confirmationToken.getConfirmedAt() != null || expiredAt.isBefore(LocalDateTime.now())) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(FailureResponseHandler.builder()
                            .error("Token is Expired")
                            .build()
                    );
        }
        int isConfirmed = confirmationTokenServiceImp.confirmToken(token);
        if (isConfirmed > 0) {
            User customer = (User) confirmationToken.getUser();
            customer.setEnabled(true);
            customer.setLocked(false);
            User savedCustomer = userRepository.save(customer);
            String accessToken = generateAccessToken(savedCustomer);
            String refreshToken = generateRefreshToken(savedCustomer);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(getSuccessfulResponse(customer, "Account Confirmed Successfully",
                            accessToken,
                            refreshToken));
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(FailureResponseHandler.builder()
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .build());
    }

    public ResponseEntity<?> login(LoginRequest authenticationReq) {
        Optional<User> customer = userRepository.getUserByUsername(authenticationReq.getUsername());
        if (customer.isPresent()) {
            try {
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        authenticationReq.getUsername(), authenticationReq.getPassword(), customer.get().getAuthorities());
                authentication = authenticationProviderService.authenticate(authentication);
                if (authentication.isAuthenticated()) {
                    String accessToken = generateAccessToken(customer.get());
                    String refreshToken = generateRefreshToken(customer.get());
                    return ResponseEntity
                            .status(HttpStatus.ACCEPTED)
                            .body(getSuccessfulResponse(customer.get(), "Logged in Successfully",
                                    accessToken, refreshToken));
                }
            } catch (Exception ex) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(FailureResponseHandler.builder()
                                .error(ex.getMessage())
                                .build());
            }
        }
        return ResponseEntity
                .badRequest()
                .body(FailureResponseHandler.builder()
                        .ok(false)
                        .error("This is " + authenticationReq.getUsername() + " email not found")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build());
    }


    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String authHeader = request.getHeader("RefreshToken");
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(FailureResponseHandler.builder()
                            .error("Token is not valid")
                            .build()
                    );
        }
        refreshToken = authHeader.substring(7);
        if (!jwtTokenUtils.isTokenExpired(refreshToken)) {
            userEmail = jwtTokenUtils.extractUsername(refreshToken);
            if (userEmail != null) {
                var user = this.userRepository.getUserByUsername(userEmail).orElseThrow();
                String newAccessToken = generateAccessToken(user);
                String newRefreshToken = generateRefreshToken(user);
                return ResponseEntity
                        .status(HttpStatus.ACCEPTED)
                        .body(getSuccessfulResponse(user, "Token Refreshed", newAccessToken, newRefreshToken));
            }
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(FailureResponseHandler
                        .builder()
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .build());
    }
    private String generateAccessToken(User user) {
        return jwtTokenUtils.generateAccessToken(user, generateExtraClaims(user));
    }
    private String generateRefreshToken(User user) {
        return jwtTokenUtils.generateRefreshToken(user, generateExtraClaims((user)));
    }
    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> claims = new HashMap();
        claims.put("id", user.getId());
        return claims;
    }

    public void sendEmail(User customer) {
        ConfirmationToken token = confirmationTokenServiceImp.saveConfirmationToken(customer);
        String link = EMAIL_CONFORMATION_PREFIX + token.getToken();
        // Log the token and link if needed
        // log.info(token.getToken());
        // log.info(link);
        emailServiceImp.sendMail(customer, link);
    }
    public ResponseEntity<String> forgotPassword(String email) {
        Optional<User> user = userRepository.getUserByUsername(email);
        if (user.isPresent()) {
            String otp = OtpUtil.generateOtp();
            otpService.saveOtp(email, otp);
            emailServiceImp.sendOtpEmail(email, "Password Reset OTP", "Your OTP is: " + otp);
            return ResponseEntity.ok("OTP sent to your email.");
        }
        else {
            throw new RuntimeException("user not found");
        }
    }
    public ResponseEntity<String> resetPassword(String email, String otp, String newPassword) {
        Optional<Otp> otpEntity = otpService.findByEmailAndOtp(email, otp);
        if (otpEntity.isPresent() && otpEntity.get().getExpirationTime().isAfter(LocalDateTime.now())) {
           Optional<User> user = userRepository.getUserByUsername(email);
           if (user.isPresent()){
               user.get().setPassword(passwordEncoder.encode(newPassword));
               userRepository.save(user.get());
           }
            return ResponseEntity.ok("Password reset successful.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP.");
        }
    }
}
