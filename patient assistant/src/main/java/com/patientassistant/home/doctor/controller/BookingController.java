package com.patientassistant.home.doctor.controller;

import com.patientassistant.home.doctor.dto.BookingInput;
import com.patientassistant.home.doctor.dto.ClinicDto;
import com.patientassistant.home.doctor.dto.DoctorAvailabilityInput;
import com.patientassistant.home.doctor.dto.DoctorDto;
import com.patientassistant.home.doctor.entity.Booking;
import com.patientassistant.home.doctor.entity.Clinic;
import com.patientassistant.home.doctor.services.BookingService;
import com.patientassistant.home.doctor.services.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping
    public BookingInput addBooking(@RequestBody BookingInput bookingInput){
        return bookingService.createBooking(bookingInput);
    }
}
