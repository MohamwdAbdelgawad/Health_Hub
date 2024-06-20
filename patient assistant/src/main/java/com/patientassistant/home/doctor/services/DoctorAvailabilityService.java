package com.patientassistant.home.doctor.services;


import com.patientassistant.home.doctor.entity.Appointment;
import com.patientassistant.home.doctor.entity.DoctorAvailability;
import com.patientassistant.home.doctor.repository.AppointmentRepository;
import com.patientassistant.home.doctor.repository.DoctorAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorAvailabilityService {

    @Autowired
    private DoctorAvailabilityRepository doctorAvailabilityRepository;

    public DoctorAvailability saveOrUpdateDoctorAvailability(DoctorAvailability doctorAvailability) {
        DoctorAvailability existingDoctorAvailability = doctorAvailabilityRepository.findByClinicIdAndDayAndDoctorId(
                doctorAvailability.getClinic().getId(),
                doctorAvailability.getDay(),
                doctorAvailability.getDoctor().getUId()
        );

        if (existingDoctorAvailability != null) {
            existingDoctorAvailability.setAvailable(doctorAvailability.isAvailable());
            existingDoctorAvailability.setStartTime(doctorAvailability.getStartTime());
            existingDoctorAvailability.setEndTime(doctorAvailability.getEndTime());
            // Add any other fields that need to be updated
            return doctorAvailabilityRepository.save(existingDoctorAvailability);
        } else {
            return doctorAvailabilityRepository.save(doctorAvailability);
        }
    }
}
