package com.lambda.foodtrucktrackr.services;

import com.lambda.foodtrucktrackr.models.MenuRating;

public interface MenuRatingService {
    MenuRating findMenuratingById(long id);
    MenuRating save(MenuRating menurating);
}
