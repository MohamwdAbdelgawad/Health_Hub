package com.patientassistant.home.doctor.services;

import com.patientassistant.home.doctor.dto.ClinicDto;
import com.patientassistant.home.doctor.dto.DoctorAvailabilityInput;
import com.patientassistant.home.doctor.dto.DoctorDto;
import com.patientassistant.home.doctor.entity.Appointment;
import com.patientassistant.home.doctor.entity.Clinic;
import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.entity.DoctorAvailability;
import com.patientassistant.home.doctor.repository.AppointmentRepository;
import com.patientassistant.home.doctor.repository.ClinicRepository;
import com.patientassistant.home.doctor.repository.DoctorAvailabilityRepository;
import com.patientassistant.home.doctor.repository.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClinicService {
    private final ClinicRepository clinicRepository;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private ModelMapper modelMapper;
    private final DoctorAvailabilityService doctorAvailabilityService;
    private AppointmentService appointmentRepository;
    private DoctorRepository doctorRepository;
    private DoctorService doctorService;
    @Qualifier("clinicModelMapper")
    private ModelMapper clinicMapper;
    @Autowired
    public ClinicService(ClinicRepository clinicRepository , DoctorRepository doctorRepository ,
                         DoctorAvailabilityRepository doctorAvailabilityRepository ,
                         DoctorAvailabilityService doctorAvailabilityService,
                          DoctorService doctorService,
                         AppointmentService appointmentRepository ,
                         @Qualifier("clinicModelMapper") ModelMapper clinicMapper,
                         @Qualifier("doctorModelMapper") ModelMapper modelMapper){
       this.clinicRepository = clinicRepository;
       this.doctorRepository = doctorRepository;
       this.doctorAvailabilityRepository = doctorAvailabilityRepository;
       this.appointmentRepository = appointmentRepository;
       this.clinicMapper = clinicMapper;
       this.doctorAvailabilityService = doctorAvailabilityService;
       this.modelMapper = modelMapper;
       this.doctorService = doctorService;
    }
    public ClinicDto convertToDto(Clinic clinic) {
        return clinicMapper.map(clinic, ClinicDto.class);
    }

    public ClinicDto addClinic(Clinic clinic , long doctorId){
        Doctor d = doctorRepository.getDoctorById(doctorId);
        clinic.setDoctor(d);
        Clinic c = clinicRepository.save(clinic);
        return this.convertToDto(c);
    }
    public ClinicDto updateClinic(Clinic clinic , long doctorId){
        Doctor d = doctorRepository.getDoctorById(doctorId);
        clinic.setDoctor(d);
        Clinic c = clinicRepository.save(clinic);
        return this.convertToDto(c);
    }

    public List<ClinicDto> getClinicByDoctor(long doctorId){
        List<Clinic> clinics = clinicRepository.getClinicsByDoctorId(doctorId);
        return clinics.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    public ClinicDto getClinicById(long clinicId){
        Clinic clinics = clinicRepository.getClinicById(clinicId);
        return convertToDto(clinics);
    }
    public List<DoctorAvailability> saveDoctorAvailability(long clinicId, Map<DayOfWeek, DoctorAvailabilityInput> availabilityInput ) {
        Clinic clinic = clinicRepository.getClinicById(clinicId);
        Doctor doctor = clinic.getDoctor();
        List<DoctorAvailability> doctorAvailabilities = new ArrayList<>();
        availabilityInput.forEach((day, doctorAvailabilityInput) -> {
            DoctorAvailability availability = new DoctorAvailability();
            availability.setDoctor(doctor);
            availability.setAvailable(doctorAvailabilityInput.isStatus());
            availability.setClinic(clinic);
            availability.setDay(day);
            availability.setStartTime(doctorAvailabilityInput.getFrom());
            availability.setEndTime(doctorAvailabilityInput.getTo());
            DoctorAvailability doctorAvailability =doctorAvailabilityService.saveOrUpdateDoctorAvailability(availability);
            doctorAvailabilities.add(doctorAvailability);
            if (availability.isAvailable()) {
               makeAppointment(availability);
            }
        });

        return doctorAvailabilities;
    }
    public void makeAppointment(DoctorAvailability availability){
        int durationInMinutes = 30;
        int totalDuration = (int) (Duration.between(availability.getStartTime(),
                availability.getEndTime()).toMinutes() / durationInMinutes);
        LocalTime start = availability.getStartTime();
        List<Appointment> appointments = new ArrayList<>();
        for (int i = 0; i < totalDuration; i++) {
            Appointment appointment = new Appointment();
            appointment.setDoctor(availability.getDoctor());
            appointment.setClinic(availability.getClinic());
            appointment.setAvailable(true);
            appointment.setDay(availability.getDay());
            appointment.setStartTime(start);
            appointment.setEndTime(start.plusMinutes(durationInMinutes));
            appointments.add(appointment);
            appointmentRepository.saveOrUpdateAppointment(appointment);
            start = start.plusMinutes(durationInMinutes);
        }
        //availability.setAppointments(appointments);
    }
}
