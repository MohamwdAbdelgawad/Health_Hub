package com.patientassistant.home.doctor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clinic")
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    private double price;
    private double length;
    private double width;
    private String address;
    @ManyToOne
    @JsonBackReference("doctor-clinics")
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    @OneToMany(mappedBy = "clinic" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonManagedReference("clinic-availability")
    private List<DoctorAvailability> doctorAvailabilities;
    @OneToMany(mappedBy = "clinic" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonManagedReference("clinic-appointment")
    @JsonIgnore
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "clinic" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonManagedReference("clinic-booking")
    @JsonIgnore
    private List<Booking> bookings;

}
