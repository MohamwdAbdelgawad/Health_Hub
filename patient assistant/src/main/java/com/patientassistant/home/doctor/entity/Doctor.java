package com.patientassistant.home.doctor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "doctor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    private String uId;
    @Column(name = "name")
    private String name;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "img_path")
    private String imgPath;
    @ManyToOne(cascade = {CascadeType.PERSIST , CascadeType.DETACH,
    CascadeType.MERGE , CascadeType.REFRESH
    })
    @JoinColumn(name = "specialty_id")
    @JsonBackReference("doctor-speciality")
    private Specialty specialty;
    @Column(name = "professional_title")
    private String profTitle;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("doctor-clinics")
    private List<Clinic> clinics;

    public void setId(String id) {
        this.uId = id;
    }
    public String getId() {
        return uId;
    }
}

