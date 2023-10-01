package com.lcwd.rating.RatingService.services;

import com.lcwd.rating.RatingService.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    //create
    Rating create(Rating rating);

    //get all rating
    List<Rating> getRatings();

    //get all by UserId
    List<Rating> getRatingByUserId(String userId);


    //get All by hotel
    List<Rating> getRatingByHoteld(String hotelId);
}
