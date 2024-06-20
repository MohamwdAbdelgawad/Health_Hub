package com.patientassistant.home.doctor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.patientassistant.home.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "appointment",
        uniqueConstraints = @UniqueConstraint(columnNames = {"clinic_id", "day", "start_time"})
)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonBackReference("doctor-appointment")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    @JsonBackReference("clinic-appointment")
    private Clinic clinic;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek day;
    private boolean isAvailable;
}