package com.patientassistant.home.doctor.controller;

import com.patientassistant.home.doctor.entity.Rating;
import com.patientassistant.home.doctor.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {
    private RatingService ratingService ;
    @Autowired
    public RatingController(RatingService ratingService){
        this.ratingService = ratingService;
    }
    @PostMapping("/{doctorId}")
    public ResponseEntity<Rating> rateDoctor(@PathVariable long doctorId ,@RequestParam int rate){
        return ResponseEntity.ok(ratingService.rateDoctor(doctorId , rate));
    }
    @GetMapping("/doctorId")
    public ResponseEntity<Double> getAverageRating(@PathVariable long doctorId){
        return ResponseEntity.ok(ratingService.getAverageRating(doctorId));
    }
}
