package com.patientassistant.home.doctor.repository;

import com.patientassistant.home.doctor.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty , Long> {
    public Specialty getSpecialtyByName(String name);
    public Specialty getSpecialtyById(long id);
    public Optional<Specialty> getSpecialtiesByName(String name);

}
