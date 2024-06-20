package com.patientassistant.home.doctor.mapper;

import com.patientassistant.home.doctor.dto.DoctorDto;
import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.services.ClinicService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class DoctorMapper {
    @Bean(name = "doctorModelMapper")
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Define custom mapping from Doctor to DoctorDTO
        modelMapper.addMappings(new PropertyMap<Doctor, DoctorDto>() {

            protected void configure() {
                map().setUId(source.getUId());
                map().setSpecialty(source.getSpecialty().getName());
                map().setClinics(source.getClinics());
            }
        });

        return modelMapper;
    }
}
