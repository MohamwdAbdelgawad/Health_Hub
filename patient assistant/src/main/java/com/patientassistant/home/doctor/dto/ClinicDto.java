package com.patientassistant.home.doctor.dto;

import com.patientassistant.home.doctor.entity.DoctorAvailability;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;
@Data
public class ClinicDto {
    private long id;
    private String name;
    private double price;
    private double length;
    private double width;
    private String address;
    private String doctorId;
    private List<DoctorAvailability> doctorAvailabilities;
}
