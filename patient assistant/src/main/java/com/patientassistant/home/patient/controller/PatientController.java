package com.patientassistant.home.patient.controller;

import com.patientassistant.home.doctor.dto.DoctorDto;
import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.patient.entity.Patient;
import com.patientassistant.home.patient.services.PatientService;
import com.patientassistant.home.security.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private PatientService patientService;
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    public PatientController(PatientService patientService ,   JwtTokenUtils jwtTokenUtils){
        this.patientService = patientService;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient){
        return ResponseEntity.ok(patientService.createPatient(patient));
    }
    @PostMapping("/img/{id}")
    public String updatePatientImage(@PathVariable long id , @RequestParam MultipartFile image){
        try {
            return  patientService.updateImage(id , image);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @PutMapping
    public ResponseEntity<Patient> updatePatient(@RequestHeader("Authentication") String token , @RequestBody Patient p){
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String username = jwtTokenUtils.extractUsername(token);
        return ResponseEntity.ok(patientService.updatePatient(p, username)) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable long id){
        return ResponseEntity.ok(patientService.getPatientById(id));
    }
}
