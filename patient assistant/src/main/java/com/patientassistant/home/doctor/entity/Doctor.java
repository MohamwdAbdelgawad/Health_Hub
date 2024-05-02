package com.patientassistant.home.doctor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
    @JsonBackReference
    private Specialty specialty;
    @Column(name = "professional_title")
    private String profTitle;

    public Doctor() {
    }

    public Doctor(long id, String name, Date birthDate, String phoneNumber,
                  String email, Gender gender, String imgPath , String profTitle) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.imgPath = imgPath;
        this.profTitle = profTitle;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public String getProfTitle() {
        return profTitle;
    }

    public void setProfTitle(String profTitle) {
        this.profTitle = profTitle;
    }
}

enum Gender{
    Male , Female
}
