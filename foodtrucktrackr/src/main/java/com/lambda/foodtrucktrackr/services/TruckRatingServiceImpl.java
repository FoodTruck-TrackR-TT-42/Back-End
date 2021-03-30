package com.lambda.foodtrucktrackr.services;

import com.lambda.foodtrucktrackr.exceptions.ResourceNotFoundException;
import com.lambda.foodtrucktrackr.models.TruckRating;
import com.lambda.foodtrucktrackr.repositories.TruckRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "truckRatingService")
public class TruckRatingServiceImpl implements TruckRatingService {
    @Autowired
    private TruckRatingRepository truckRatingRepository;

    @Autowired
    private TruckService truckService;

    @Autowired
    private UserService userService;

    @Override
    public TruckRating findTruckratingById(long id) {
        return truckRatingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Truck rating id " + id + " not found!"));
    }

    @Override
    public TruckRating save(TruckRating truckrating) {
        TruckRating newRating = new TruckRating();

        long oldid = truckrating.getTruckratingid();

        if (oldid != 0) {
            truckRatingRepository.findById(oldid)
                    .orElseThrow(() -> new ResourceNotFoundException("Truck rating id " + oldid + " not found!"));
            newRating.setTruckratingid(oldid);
        }

        newRating.setScore(truckrating.getScore());

        if (truckrating.getTruck() != null) {
            newRating.setTruck(truckService.findTruckById(truckrating.getTruck().getTruckid()));
        }
        if (truckrating.getUser() != null) {
            newRating.setUser(userService.findUserById(truckrating.getUser().getUserid()));
        }

        return truckRatingRepository.save(newRating);
    }
}
