package com.patientassistant.home.doctor.repository;

import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor , Long> {
    public Doctor getDoctorById(long id);
    public List<Doctor> getDoctorsByName(String name);
    public List<Doctor> getDoctorsBySpecialtyId(long id);

}
