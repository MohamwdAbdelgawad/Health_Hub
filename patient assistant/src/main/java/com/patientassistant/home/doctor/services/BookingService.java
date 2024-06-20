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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private ClinicRepository clinicRepository;
    private BookingRepository bookingRepository;
    @Autowired
    public BookingService( DoctorRepository doctorRepository ,
                           PatientRepository patientRepository,
                           ClinicRepository clinicRepository,
                           BookingRepository bookingRepository)
    {
        this.doctorRepository = doctorRepository;
        this.clinicRepository = clinicRepository;
        this.patientRepository = patientRepository;
        this.bookingRepository = bookingRepository;
    }
    public Booking createBooking(BookingInput bookingInput){
        Doctor doctor = doctorRepository.getDoctorById(bookingInput.getDoctorId());
        Patient patient = patientRepository.getPatientsById(bookingInput.getPatientId());
        Clinic clinic = clinicRepository.getClinicById(bookingInput.getClinicId());
        Booking booking = new Booking();
        booking.setDoctor(doctor);
        booking.setPatient(patient);
        booking.setClinic(clinic);
        booking.setBookingDate(bookingInput.getBookingDate());
        booking.setStartTime(bookingInput.getStartTime());
        booking.setEndTime(bookingInput.getEndTime());
        return  bookingRepository.save(booking);

    }

}
