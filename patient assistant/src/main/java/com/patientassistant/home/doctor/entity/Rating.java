package com.patientassistant.home.doctor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonBackReference("doctor-rating")
    private Doctor doctor;

    private double rating; // Rating can be an integer (1-5 stars, for example)

    // Constructors, getters, setters
}
