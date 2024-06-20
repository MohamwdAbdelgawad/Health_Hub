package com.patientassistant.home.doctor.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DoctorAvailabilityInput {
    private boolean status;
    private LocalTime from;
    private LocalTime to;


}
