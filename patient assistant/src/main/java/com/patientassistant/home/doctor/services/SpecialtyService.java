package com.patientassistant.home.doctor.services;

import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.entity.Specialty;
import com.patientassistant.home.doctor.repository.DoctorRepository;
import com.patientassistant.home.doctor.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Specialty> getByName(String name){
        return Optional.ofNullable(specialtyRepository.getSpecialtyByName(name));
    }
    public Specialty saveOrUpdateSpecialty(Specialty specialty) {
        Optional<Specialty> existingSpecialty = specialtyRepository.getSpecialtiesByName(specialty.getName());
        if (existingSpecialty.isPresent()) {
            Specialty existing = existingSpecialty.get();
            existing.setName(specialty.getName());
            existing.setDoctors(specialty.getDoctors());
            return specialtyRepository.save(existing);
        } else {
            return specialtyRepository.save(specialty);
        }
    }
    public Specialty getById(long id){
        return specialtyRepository.getSpecialtyById(id);
    }
    public List<Doctor> getSpecialtyDoctors(long id){
        return getById(id).getDoctors();
    }
}
