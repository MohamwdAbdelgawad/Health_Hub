package com.patientassistant.home.doctor.mapper;

import com.patientassistant.home.doctor.dto.DoctorDto;
import com.patientassistant.home.doctor.entity.Doctor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DoctorMapper {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Define custom mapping from Doctor to DoctorDTO
        modelMapper.addMappings(new PropertyMap<Doctor, DoctorDto>() {

            protected void configure() {
                //map().setUId(source.getId());
                map().setSpecialty(source.getSpecialty().getName());
            }
        });

        return modelMapper;
    }
}
