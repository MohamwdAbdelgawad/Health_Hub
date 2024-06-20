package com.patientassistant.home.doctor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.patientassistant.home.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JsonBackReference("clinic-booking")
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate bookingDate;
    @ManyToOne
    @JsonBackReference("patient-booking")
    @JoinColumn(name = "patient_id" , nullable = false)
    private Patient patient;

}
