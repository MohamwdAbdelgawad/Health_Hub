package com.patientassistant.home.patient.controller;

import com.patientassistant.home.patient.entity.Patient;
import com.patientassistant.home.patient.services.PatientService;
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
    @Autowired
    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient){
        return ResponseEntity.ok(patientService.createPatient(patient));
    }
    @PostMapping("/img/{id}")
    public String updatePatientImage(@PathVariable String id , @RequestParam MultipartFile image){
        try {
            return  patientService.updateImage(id , image);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id){
        return ResponseEntity.ok(patientService.getPatientById(id));
    }
}
