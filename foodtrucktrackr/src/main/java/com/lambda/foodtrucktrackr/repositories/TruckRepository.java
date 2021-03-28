package com.lambda.foodtrucktrackr.repositories;

import com.lambda.foodtrucktrackr.models.Truck;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TruckRepository extends CrudRepository<Truck, Long> {
    List<Truck> findByCuisinetypeContainingIgnoringCase(String cuisineType);
}
