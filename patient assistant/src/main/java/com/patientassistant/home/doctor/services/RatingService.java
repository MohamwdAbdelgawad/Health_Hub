package com.patientassistant.home.doctor.services;

import com.patientassistant.home.doctor.entity.Doctor;
import com.patientassistant.home.doctor.entity.Rating;
import com.patientassistant.home.doctor.repository.DoctorRepository;
import com.patientassistant.home.doctor.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private RatingRepository ratingRepository;
    private DoctorRepository doctorRepository;
    @Autowired
    public RatingService(RatingRepository ratingRepository , DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
        this.ratingRepository = ratingRepository;
    }

    public Rating rateDoctor(String doctorId, int rating) {
        Doctor doctor = doctorRepository.getDoctorById(doctorId);
        Rating newRating = new Rating();
        newRating.setDoctor(doctor);
        newRating.setRating(rating);
        return ratingRepository.save(newRating);
    }

    public double getAverageRating(String doctorId) {
        List<Rating> ratings = ratingRepository.getRatingByDoctorId(doctorId);
        if (ratings.isEmpty()) {
            return 0.0;
        }
        double sum = ratings.stream().mapToDouble(Rating::getRating).sum();
        return sum / ratings.size();
    }
}
