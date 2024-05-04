package com.patientassistant.home.doctor.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.patientassistant.home.doctor.entity.Specialty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
public class DoctorDto {
    private String uId;
    private String name;
    private Date birthDate;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private String imgPath;
    private String specialty;
    private String profTitle;

}
 enum Gender {
    Male , Female
}
