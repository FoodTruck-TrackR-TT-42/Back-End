package com.lambda.foodtrucktrackr.repositories;

import com.lambda.foodtrucktrackr.models.TruckLocation;
import org.springframework.data.repository.CrudRepository;

public interface TruckLocationRepository extends CrudRepository<TruckLocation, Long> {
}
