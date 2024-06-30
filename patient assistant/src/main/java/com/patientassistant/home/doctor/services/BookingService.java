package com.patientassistant.home.doctor.services;

import com.patientassistant.home.doctor.dto.BookingInput;
import com.patientassistant.home.doctor.entity.Booking;
import com.patientassistant.home.doctor.entity.Clinic;
import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.repository.BookingRepository;
import com.patientassistant.home.doctor.repository.ClinicRepository;
import com.patientassistant.home.doctor.repository.DoctorRepository;
import com.patientassistant.home.patient.entity.Patient;
import com.patientassistant.home.patient.repository.PatientRepository;
import com.patientassistant.home.security.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private ClinicRepository clinicRepository;
    private BookingRepository bookingRepository;
    private JwtTokenUtils jwtTokenUtils ;
    @Autowired
    public BookingService( DoctorRepository doctorRepository ,
                           PatientRepository patientRepository,
                           ClinicRepository clinicRepository,
                           BookingRepository bookingRepository ,
                           JwtTokenUtils jwtTokenUtils )
    {
        this.doctorRepository = doctorRepository;
        this.clinicRepository = clinicRepository;
        this.patientRepository = patientRepository;
        this.bookingRepository = bookingRepository;
        this.jwtTokenUtils = jwtTokenUtils;
    }
    public BookingInput createBooking(String username ,BookingInput bookingInput){
        Doctor doctor = doctorRepository.getDoctorById(bookingInput.getDoctorId());
        Optional<Patient> patient = patientRepository.getPatientByUsername(username);
        Clinic clinic = clinicRepository.getClinicById(bookingInput.getClinicId());
        Booking booking = new Booking();
        booking.setDoctor(doctor);
        booking.setPatient(patient.get());
        booking.setClinic(clinic);
        booking.setBookingDate(bookingInput.getBookingDate());
        booking.setStartTime(bookingInput.getStartTime());
        booking.setEndTime(bookingInput.getEndTime());
        Booking b =  bookingRepository.save(booking);
        BookingInput bookingInput1 = new BookingInput(b.getDoctor().getId() , b.getClinic().getId() , b.getStartTime(),
                b.getEndTime() , b.getBookingDate() , b.getPatient().getId());
        return bookingInput1;

    }

}
