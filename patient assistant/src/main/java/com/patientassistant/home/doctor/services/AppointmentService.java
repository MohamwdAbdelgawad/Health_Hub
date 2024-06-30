package com.patientassistant.home.doctor.services;


import com.patientassistant.home.doctor.entity.Appointment;
import com.patientassistant.home.doctor.entity.Clinic;
import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.repository.AppointmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    @PersistenceContext
    private EntityManager em ;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment saveOrUpdateAppointment(Appointment appointment) {
        Appointment existingAppointment = appointmentRepository.findByClinicIdAndDayAndStartTime(
                appointment.getClinic().getId(),
                appointment.getDay(),
                appointment.getStartTime()
        );

        if (existingAppointment != null) {
            existingAppointment.setDoctor(appointment.getDoctor());
            existingAppointment.setEndTime(appointment.getEndTime());
            existingAppointment.setAvailable(appointment.isAvailable());
            // Add any other fields that need to be updated
            return appointmentRepository.save(existingAppointment);
        } else {
            return appointmentRepository.save(appointment);
        }
    }
    public List<Appointment> findAvailableAppointmentsForDoctorByDate(long clinicId, LocalDate date) {
        Clinic clinic = em.find(Clinic.class, clinicId);

                // Filter appointments for the doctor on the specific day
                Query query1 = em.createQuery("SELECT a FROM Appointment a " +
                        "WHERE a.clinic = :clinic AND a.day = :dayOfWeek");

        query1.setParameter("clinic", clinic);
        query1.setParameter("dayOfWeek", date.getDayOfWeek()); // Get DayOfWeek from LocalDate

        List<Appointment> allAppointments = query1.getResultList();

        // Check for existing bookings on those appointments
        List<Appointment> availableAppointments = new ArrayList<>();
        for (Appointment appointment : allAppointments) {
            boolean isBooked = false;

            // Use a subquery to check for bookings on the specific appointment date and time
            Query query2 = em.createQuery(
                    "SELECT b FROM Booking b " +
                            "WHERE b.clinic = :clinic AND b.bookingDate = :date AND b.startTime = :startTime AND b.endTime = :endTime");

            query2.setParameter("clinic", clinic);
            query2.setParameter("date", date);
            query2.setParameter("startTime", appointment.getStartTime());
            query2.setParameter("endTime", appointment.getEndTime());

            if (query2.getResultList().isEmpty()) {
                isBooked = false; // No booking found, appointment is available
            } else {
                isBooked = true; // Booking found, appointment is not available
            }

            if (!isBooked) {
                availableAppointments.add(appointment);
            }
        }

        return availableAppointments;
    }

}
