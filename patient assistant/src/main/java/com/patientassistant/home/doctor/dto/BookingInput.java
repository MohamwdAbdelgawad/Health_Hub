package com.patientassistant.home.doctor.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
public class BookingInput {
    private long doctorId;
    private long clinicId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate bookingDate;
    private long patientId;
}
