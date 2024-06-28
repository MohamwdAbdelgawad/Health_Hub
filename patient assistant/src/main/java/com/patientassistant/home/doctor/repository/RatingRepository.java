package com.patientassistant.home.doctor.repository;

import com.patientassistant.home.doctor.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating , Long> {
    List<Rating> getRatingByDoctorId(long doctorId);
}
