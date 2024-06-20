package com.patientassistant.home.doctor.repository;

import com.patientassistant.home.doctor.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByClinicIdAndDayAndStartTime(Long clinicId, DayOfWeek day, LocalTime startTime);
}