package com.patientassistant.home.doctor.repository;

import com.patientassistant.home.doctor.entity.Appointment;
import com.patientassistant.home.doctor.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking>  findBookingByDoctorId(long  doctorId);
}