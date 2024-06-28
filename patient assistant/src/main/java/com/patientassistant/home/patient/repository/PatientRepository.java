package com.patientassistant.home.patient.repository;

import com.patientassistant.home.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient , Long> {
public Patient getPatientById(long id);
public Optional<Patient> getPatientByUsername(String email);
}
