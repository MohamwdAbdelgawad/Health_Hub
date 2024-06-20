package com.patientassistant.home.doctor.dto;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class BookingInput {
    private String doctorId;
    private long clinicId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate bookingDate;
    private String patientId;
}
