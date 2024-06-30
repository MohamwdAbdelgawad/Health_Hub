package com.patientassistant.home.patient.services;

import com.patientassistant.home.aws.service.ImageUploadService;
import com.patientassistant.home.doctor.dto.DoctorDto;
import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.patient.entity.Patient;
import com.patientassistant.home.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {
    private String profilImgDirectory = "D:/Spring/chad project/spring boot 3 and hibernate/Cource code by me/patient assistant/profileImg";
    private PatientRepository patientRepository;
    private ImageUploadService imageUploadService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PatientService(PatientRepository patientRepository , ImageUploadService imageUploadService,
                          PasswordEncoder passwordEncoder){
        this.patientRepository  = patientRepository;
        this.imageUploadService = imageUploadService;
        this.passwordEncoder = passwordEncoder;

    }
    public Patient getPatientById(long id){
        return patientRepository.getPatientById(id);
    }
    public Patient createPatient(Patient patient) {
        patient.setEnabled(true);
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        return patientRepository.save(patient);
    }
    public Patient updatePatient(Patient patient){
        return patientRepository.save(patient);
    }
    public void deletPatient(Patient patient){
         patientRepository.delete(patient);

    }
    public Optional<Patient> getDoctorByUsername(String username){
        return Optional.ofNullable(patientRepository.getPatientByUsername(username).orElse(null));
    }
    public Patient updatePatient(Patient patient , String userName){
        Optional<Patient> p = patientRepository.getPatientByUsername(userName);
        if(p.isPresent()){
           patient.setId(p.get().getId());
           patient.setUsername(p.get().getUsername());
           patient.setPassword(p.get().getPassword());
        }
        return patientRepository.save(patient);
    }
    public String updateImage(long id , MultipartFile image) throws IOException {
        Patient d = patientRepository.getPatientById(id);
        String url = imageUploadService.uploadFile(image);
        d.setImgPath(url);
        patientRepository.save(d);
        return url;
//        try {
//            String originalFileName = image.getOriginalFilename();
//            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
//            String fileName = p.getId() + "_" + System.currentTimeMillis() + "." + fileExtension;
//            String filePath = profilImgDirectory + File.separator + fileName;
//            saveImageToFile(image.getBytes(), filePath);
//            p.setImgPath(filePath); // Save the file name or path in the UserProfile entity
//            updatePatient(p);
//        } catch (IOException e) {
//            e.printStackTrace(); // Handle the exception appropriately
//        }


    }
    private void saveImageToFile(byte[] imageData, String filePath) {
        try {
            Files.write(Paths.get(filePath), imageData);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }


}
