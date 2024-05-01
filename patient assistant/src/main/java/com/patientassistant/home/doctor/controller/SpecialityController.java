package com.patientassistant.home.doctor.controller;

import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.entity.Specialty;
import com.patientassistant.home.doctor.services.DoctorService;
import com.patientassistant.home.doctor.services.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/speciality")
public class SpecialityController {
    private SpecialtyService specialtyService;
    @Autowired
    public SpecialityController(SpecialtyService specialtyService){
        this.specialtyService = specialtyService;
    }
    @GetMapping
    public ResponseEntity<List<Specialty>> getAll(){
        return ResponseEntity.ok(specialtyService.getAllSpeciality());
    }
    @GetMapping("/{id}/doctors")
    public ResponseEntity<List<Doctor>> getSpecialtyDoctors(@PathVariable long id){
        return ResponseEntity.ok(specialtyService.getSpecialtyDoctors(id));
    }


}
