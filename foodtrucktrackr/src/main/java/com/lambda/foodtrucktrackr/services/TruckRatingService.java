package com.lambda.foodtrucktrackr.services;

import com.lambda.foodtrucktrackr.models.TruckRating;

public interface TruckRatingService {
    TruckRating findTruckratingById(long id);
    TruckRating save(TruckRating truckrating);
}
