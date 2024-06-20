package com.patientassistant.home.doctor.controller;
import com.patientassistant.home.doctor.entity.Appointment;
import com.patientassistant.home.doctor.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private AppointmentService appointmentService;
    @Autowired
    public AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }
    @GetMapping()
    public List<Appointment> findAvailableAppointmentsForDoctorByDate(@RequestParam String doctorId,@RequestParam String dateString){
        LocalDate localDate = LocalDate.parse(dateString);
        return appointmentService.findAvailableAppointmentsForDoctorByDate(doctorId , localDate);
    }
}
