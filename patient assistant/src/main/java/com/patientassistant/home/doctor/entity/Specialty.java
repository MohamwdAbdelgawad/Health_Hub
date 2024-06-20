package com.patientassistant.home.doctor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "specialty")
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "specialty")
    @JsonManagedReference("doctor-speciality")
    private List<Doctor> doctors;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
    public void addDoctor(Doctor d ){
        if(doctors == null){
            doctors = new ArrayList<>();
        }
        doctors.add(d);
    }
}
