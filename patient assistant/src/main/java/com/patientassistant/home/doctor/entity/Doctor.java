package com.patientassistant.home.doctor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.patientassistant.home.security.entites.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "doctor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor extends User {

    @Column(name = "name")
    private String name;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "img_path")
    private String imgPath;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialty_id")
    @JsonBackReference("doctor-speciality")
    private Specialty specialty;
    @Column(name = "professional_title")
    private String profTitle;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("doctor-clinics")
    private List<Clinic> clinics;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("doctor-rating")
    private List<Rating> ratings;

    public double calculateRating() {
        return this.getRatings().stream()
                .mapToDouble(Rating::getRating)
                .average()
                .orElse(0.0);
    }
}