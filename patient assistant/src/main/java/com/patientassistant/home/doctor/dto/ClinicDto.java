package com.patientassistant.home.doctor.dto;

import com.patientassistant.home.doctor.entity.DoctorAvailability;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;
@Data
public class ClinicDto {
    private long id;
    private String name;
    private String phone;
    private double examination;
    private double followUp;
    private double latitude;
    private double longitude;
    private String address;
    private long doctorId;
    private List<DoctorAvailability> doctorAvailabilities;
}
