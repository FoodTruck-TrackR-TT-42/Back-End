package com.lambda.foodtrucktrackr.services;

import com.lambda.foodtrucktrackr.exceptions.ResourceNotFoundException;
import com.lambda.foodtrucktrackr.models.MenuRating;
import com.lambda.foodtrucktrackr.repositories.MenuRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "menuRatingService")
public class MenuRatingServiceImpl implements MenuRatingService {
    @Autowired
    private MenuRatingRepository menuRatingRepository;

    @Override
    public MenuRating findMenuratingById(long id) {
        return menuRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu rating id " + id + " not found!"));
    }

    @Override
    public MenuRating save(MenuRating menurating) {
        MenuRating newRating = new MenuRating();

        long oldid = menurating.getMenuratingid();

        if (oldid != 0) {
            menuRatingRepository.findById(oldid)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu rating id " + oldid + " not found!"));
            newRating.setMenuratingid(oldid);
        }

        newRating.setScore(menurating.getScore());
        newRating.setMenuitem(menurating.getMenuitem());
        newRating.setUser(menurating.getUser());

        return menuRatingRepository.save(newRating);
    }
}
