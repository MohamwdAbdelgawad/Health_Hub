package com.patientassistant.home.doctor.controller;

import com.patientassistant.home.doctor.dto.DoctorDto;
import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.services.DoctorService;
import com.patientassistant.home.security.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private DoctorService doctorService;
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    public DoctorController(DoctorService doctorService , JwtTokenUtils jwtTokenUtils){
        this.doctorService = doctorService;
        this.jwtTokenUtils = jwtTokenUtils;
    }
    @PostMapping
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor d){
        return ResponseEntity.ok(doctorService.addDoctor(d));
    }
    @PutMapping
    public ResponseEntity<DoctorDto> updateDoctor(@RequestHeader("Authentication") String token , @RequestBody Doctor d){
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String username = jwtTokenUtils.extractUsername(token);
        return ResponseEntity.ok(doctorService.updateDoctor(d , username)) ;
    }
    @DeleteMapping
    public void deleteDoctor(Doctor d){
        doctorService.deleteDoctor(d);
    }
    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors(){
        return ResponseEntity.ok( doctorService.getAllDoctors());
    }
    @GetMapping("/{id}")
    public DoctorDto getDoctorById(@PathVariable long id){

        return doctorService.getDoctorById(id);
    }
    @GetMapping("/name/{name}")
    public List<DoctorDto> getDoctorsByName(@PathVariable String name){
        return doctorService.getDoctorsByName(name);
    }
    @GetMapping("/speciality/{id}")
    public List<DoctorDto> getDoctorsBySpecialtyId(@PathVariable long id){
        return doctorService.getDoctorsBySpecialtyId(id);
    }
    @PostMapping("/img/{id}")
    public String updateDoctorImage(@PathVariable long id , @RequestParam MultipartFile image){
        try {
          return  doctorService.updateImage(id , image);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
