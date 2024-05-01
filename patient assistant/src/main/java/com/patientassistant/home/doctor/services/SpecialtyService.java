package com.patientassistant.home.doctor.services;

import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.entity.Specialty;
import com.patientassistant.home.doctor.repository.DoctorRepository;
import com.patientassistant.home.doctor.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyService {
    private SpecialtyRepository specialtyRepository;
    public SpecialtyService(){

    }
    @Autowired
    public SpecialtyService(SpecialtyRepository specialtyRepository){
        this.specialtyRepository = specialtyRepository;
    }
    public List<Specialty> getAllSpeciality(){
        return  specialtyRepository.findAll();
    }
    public Specialty getByName(String name){
        return specialtyRepository.getSpecialtyByName(name);
    }
    public Specialty addSpeciality(Specialty s){
        return specialtyRepository.save(s);
    }
    public Specialty getById(long id){
        return specialtyRepository.getSpecialtyById(id);
    }
    public List<Doctor> getSpecialtyDoctors(long id){
        return getById(id).getDoctors();
    }
}
