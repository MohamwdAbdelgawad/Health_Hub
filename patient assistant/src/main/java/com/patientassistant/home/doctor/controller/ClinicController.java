package com.patientassistant.home.doctor.controller;

import com.patientassistant.home.doctor.dto.ClinicDto;
import com.patientassistant.home.doctor.dto.DoctorAvailabilityInput;
import com.patientassistant.home.doctor.dto.DoctorDto;
import com.patientassistant.home.doctor.entity.Clinic;
import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.entity.DoctorAvailability;
import com.patientassistant.home.doctor.services.ClinicService;
import com.patientassistant.home.doctor.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clinic")
public class ClinicController {
    private ClinicService clinicService;
    @Autowired
    public ClinicController(ClinicService clinicService){
        this.clinicService = clinicService;
    }
    @PostMapping("/{doctorId}")
    public ClinicDto addClinic(@RequestBody Clinic clinic ,@PathVariable long doctorId){
        return clinicService.addClinic(clinic , doctorId);
    }
    @PutMapping("/{doctorId}")
    public ClinicDto updateClinic(@RequestBody Clinic clinic , @PathVariable long doctorId){
        return clinicService.updateClinic(clinic , doctorId);
    }
    @PostMapping("/{clinicId}/availability")
    public List<DoctorAvailability> saveDoctorAvailability(@PathVariable Long clinicId, @RequestBody Map<DayOfWeek,
            DoctorAvailabilityInput> availabilityInput )
    {
        return clinicService.saveDoctorAvailability(clinicId, availabilityInput);
    }
    @GetMapping("/by-doctor")
    public ResponseEntity<List<ClinicDto>> getClinicByDoctor(@RequestParam long doctorId){
        return ResponseEntity.ok(clinicService.getClinicByDoctor(doctorId));
    }
    @GetMapping("/by-id")
    public ResponseEntity<ClinicDto> getClinicById(@RequestParam long clinicId){
        return ResponseEntity.ok(clinicService.getClinicById(clinicId));
    }

}
