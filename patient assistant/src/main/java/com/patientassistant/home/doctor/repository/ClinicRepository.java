package com.patientassistant.home.doctor.repository;

import com.patientassistant.home.doctor.entity.Clinic;
import com.patientassistant.home.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    public Clinic getClinicById(long id);
    public List<Clinic> getClinicsByDoctorId(String doctorId);

}
