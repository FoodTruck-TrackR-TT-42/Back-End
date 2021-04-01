package com.lambda.foodtrucktrackr.services;

import com.lambda.foodtrucktrackr.exceptions.ResourceNotFoundException;
import com.lambda.foodtrucktrackr.models.TruckLocation;
import com.lambda.foodtrucktrackr.repositories.TruckLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "truckLocationService")
public class TruckLocationServiceImpl implements TruckLocationService {
    @Autowired
    private TruckLocationRepository truckLocationRepository;

    @Autowired
    private TruckService truckService;

    @Override
    public TruckLocation findTrucklocationById(long id) {
        return truckLocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Truck location id " + id + " not found!"));
    }

    @Override
    public TruckLocation save(TruckLocation trucklocation) {
        TruckLocation newLocation = new TruckLocation();

        long oldid = trucklocation.getTrucklocationid();

        if (oldid != 0) {
            truckLocationRepository.findById(oldid)
                    .orElseThrow(() -> new ResourceNotFoundException("Truck location id " + oldid + " not found!"));
            newLocation.setTrucklocationid(oldid);
        }

        newLocation.setDeparturetime(trucklocation.getDeparturetime());
        newLocation.setLatitude(trucklocation.getLatitude());
        newLocation.setLongitude(trucklocation.getLongitude());

        if (trucklocation.getTruck() != null) {
            newLocation.setTruck(truckService.findTruckById(trucklocation.getTruck().getTruckid()));
        }

        return truckLocationRepository.save(newLocation);
    }
}
