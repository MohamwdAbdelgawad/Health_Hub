package com.patientassistant.home.doctor.controller;

import com.patientassistant.home.doctor.dto.DoctorDto;
import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.entity.Specialty;
import com.patientassistant.home.doctor.services.DoctorService;
import com.patientassistant.home.patient.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private DoctorService doctorService;
    @Autowired
    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }
    @PostMapping
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor d){
        return ResponseEntity.ok(doctorService.addDoctor(d));
    }
    @PutMapping
    public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor d){
        return ResponseEntity.ok(doctorService.updateDoctor(d)) ;
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
    public DoctorDto getDoctorById(@PathVariable String id){
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
    public String updateDoctorImage(@PathVariable String id , @RequestParam MultipartFile image){
        try {
          return  doctorService.updateImage(id , image);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
