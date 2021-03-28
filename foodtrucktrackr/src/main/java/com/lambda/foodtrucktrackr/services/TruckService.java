package com.lambda.foodtrucktrackr.services;

import com.lambda.foodtrucktrackr.models.Truck;

import java.util.List;

public interface TruckService {
    List<Truck> findAll();
    List<Truck> findTruckById(long id);
    List<Truck> findTrucksByUserId(long id);
    List<Truck> findTrucksByCuisineType(String cuisineType);
    Truck save(Truck truck);
    Truck update(Truck truck, long id);
}
