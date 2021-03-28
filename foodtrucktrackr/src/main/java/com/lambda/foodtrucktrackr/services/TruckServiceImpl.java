package com.lambda.foodtrucktrackr.services;

import com.lambda.foodtrucktrackr.models.Truck;
import com.lambda.foodtrucktrackr.repositories.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "truckService")
public class TruckServiceImpl  implements TruckService {
    @Autowired
    private TruckRepository truckrepos;

    @Override
    public List<Truck> findAll() {
        List<Truck> trucks = new ArrayList<>();

        truckrepos.findAll()
                .iterator()
                .forEachRemaining(trucks::add);

        return trucks;
    }

    @Override
    public List<Truck> findTruckById(long id) {
        return null;
    }

    @Override
    public List<Truck> findTrucksByUserId(long id) {
        return null;
    }

    @Override
    public List<Truck> findTrucksByCuisineType(String cuisineType) {
        return null;
    }

    @Override
    public Truck save(Truck truck) {
        return null;
    }

    @Override
    public Truck update(Truck truck, long id) {
        return null;
    }
}
