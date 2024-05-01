package com.patientassistant.home.doctor.services;

import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.entity.Specialty;
import com.patientassistant.home.doctor.repository.DoctorRepository;
import com.patientassistant.home.patient.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DoctorService {
    private String profilImgDirectory = "D:/Spring/chad project/spring boot 3 and hibernate/Cource code by me/patient assistant/profileImg";
    private DoctorRepository doctorRepository;
    @Autowired
    public DoctorService(DoctorRepository doctorRepository){
       this.doctorRepository = doctorRepository;
    }
    public Doctor addDoctor(Doctor d){
        return doctorRepository.save(d);
    }
    public Doctor updateDoctor(Doctor d){
        return doctorRepository.save(d);
    }
    public void deleteDoctor(Doctor d){
         doctorRepository.delete(d);
    }
    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }
    public Doctor getDoctorById(long id){
        return doctorRepository.getDoctorById(id);
    }
    public List<Doctor> getDoctorsByName(String name){
        return doctorRepository.getDoctorsByName(name);
    }
    public List<Doctor> getDoctorsBySpecialtyId(long id){
        return doctorRepository.getDoctorsBySpecialtyId(id);
    }
    public void updateImage(long id , MultipartFile image){
        Doctor d = getDoctorById(id);
        try {
            String originalFileName = image.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
            String fileName = d.getId() + "_" + System.currentTimeMillis() + "." + fileExtension;
            String filePath = profilImgDirectory + File.separator + fileName;
            saveImageToFile(image.getBytes(), filePath);
            d.setImgPath(filePath); // Save the file name or path in the UserProfile entity
            updateDoctor(d);
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
