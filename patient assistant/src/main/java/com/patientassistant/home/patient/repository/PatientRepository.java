package com.patientassistant.home.patient.repository;

import com.patientassistant.home.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient , String> {
public Patient getPatientsById(String id);
}
