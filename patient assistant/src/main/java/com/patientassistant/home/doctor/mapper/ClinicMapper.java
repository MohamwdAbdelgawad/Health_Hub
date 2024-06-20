package com.patientassistant.home.doctor.mapper;

import com.patientassistant.home.doctor.dto.ClinicDto;
import com.patientassistant.home.doctor.dto.DoctorDto;
import com.patientassistant.home.doctor.entity.Clinic;
import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.entity.DoctorAvailability;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ClinicMapper {
    @Bean(name = "clinicModelMapper")
    public ModelMapper clinicMapper() {
        ModelMapper clinicMapper = new ModelMapper();

        clinicMapper.addMappings(new PropertyMap<Clinic, ClinicDto>() {

            protected void configure() {
                map().setId(source.getId());
                map().setDoctorId(source.getDoctor().getUId());
                map().setDoctorAvailabilities(source.getDoctorAvailabilities());
            }
        });

        return clinicMapper;
    }
}
