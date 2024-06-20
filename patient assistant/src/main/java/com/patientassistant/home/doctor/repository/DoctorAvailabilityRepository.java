package com.patientassistant.home.doctor.repository;

import com.patientassistant.home.doctor.entity.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {
    List<DoctorAvailability> findByClinicId(Long clinicId);
    DoctorAvailability findByClinicIdAndDayAndDoctorId(Long clinicId, DayOfWeek day, String doctorId);

}
