package com.patientassistant.home.doctor.services;

import com.patientassistant.home.aws.service.ImageUploadService;
import com.patientassistant.home.doctor.dto.DoctorAvailabilityInput;
import com.patientassistant.home.doctor.dto.DoctorDto;
import com.patientassistant.home.doctor.entity.Clinic;
import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.entity.DoctorAvailability;
import com.patientassistant.home.doctor.entity.Specialty;
import com.patientassistant.home.doctor.repository.DoctorRepository;
import com.patientassistant.home.patient.entity.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private String profilImgDirectory = "D:/Spring/chad project/spring boot 3 and hibernate/Cource code by me/patient assistant/profileImg";
    private DoctorRepository doctorRepository;
    private ImageUploadService imageUploadService;
    @Qualifier("doctorModelMapper")
    private ModelMapper modelMapper;
    @Autowired
    public DoctorService(DoctorRepository doctorRepository , @Qualifier("doctorModelMapper")ModelMapper modelMapper,
                         ImageUploadService imageUploadService){
       this.doctorRepository = doctorRepository;
       this.modelMapper = modelMapper;
       this.imageUploadService = imageUploadService;
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
    public List<DoctorDto> getAllDoctors(){
        List<Doctor> doctors  =  doctorRepository.findAll();
        return doctors.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    private DoctorDto convertToDto(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDto.class);
    }
    public DoctorDto getDoctorById(String id){
        Doctor doctor =  doctorRepository.getDoctorById(id);
        return  modelMapper.map(doctor , DoctorDto.class);
    }
    public List<DoctorDto> getDoctorsByName(String name){
        List<Doctor> doctors  =  doctorRepository.getDoctorsByName(name);
        return doctors.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    public List<DoctorDto> getDoctorsBySpecialtyId(long id){
        List<Doctor> doctors  =  doctorRepository.getDoctorsBySpecialtyId(id);
        return doctors.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    public String updateImage(String id , MultipartFile image) throws IOException {
        Doctor d = doctorRepository.getDoctorById(id);
        String url = imageUploadService.uploadFile(image);
        d.setImgPath(url);
        doctorRepository.save(d);
        return url;

//        try {
//            String originalFileName = image.getOriginalFilename();
//            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
//            String fileName = d.getUId() + "_" + System.currentTimeMillis() + "." + fileExtension;
//            String filePath = profilImgDirectory + File.separator + fileName;
//            saveImageToFile(image.getBytes(), filePath);
//            d.setImgPath(filePath); // Save the file name or path in the UserProfile entity
//            updateDoctor(d);
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
