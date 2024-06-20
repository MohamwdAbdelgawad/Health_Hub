package com.patientassistant.home.patient.services;

import com.patientassistant.home.patient.entity.Patient;
import com.patientassistant.home.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class PatientService {
    private String profilImgDirectory = "D:/Spring/chad project/spring boot 3 and hibernate/Cource code by me/patient assistant/profileImg";
    private PatientRepository patientRepository;
    @Autowired
    public PatientService(PatientRepository patientRepository){
        this.patientRepository  = patientRepository;
    }
    public Patient getPatientById(String id){
        return patientRepository.getPatientsById(id);
    }
    public Patient createPatient(Patient patient){
        if (patient.getId() == null || patient.getId().isEmpty()) {
            patient.setUId(UUID.randomUUID().toString()); // or any other logic to generate the ID
        }
        return patientRepository.save(patient);
    }
    public Patient updatePatient(Patient patient){
        return patientRepository.save(patient);
    }
    public void deletPatient(Patient patient){
         patientRepository.delete(patient);

    }
    public void updateImage(String id , MultipartFile image){
        Patient p = getPatientById(id);
        try {
            String originalFileName = image.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
            String fileName = p.getId() + "_" + System.currentTimeMillis() + "." + fileExtension;
            String filePath = profilImgDirectory + File.separator + fileName;
            saveImageToFile(image.getBytes(), filePath);
            p.setImgPath(filePath); // Save the file name or path in the UserProfile entity
            updatePatient(p);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }


    }
    private void saveImageToFile(byte[] imageData, String filePath) {
        try {
            Files.write(Paths.get(filePath), imageData);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }


}
