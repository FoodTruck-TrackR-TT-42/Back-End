package com.lambda.foodtrucktrackr.services;

import com.lambda.foodtrucktrackr.models.TruckLocation;

public interface TruckLocationService {
    TruckLocation findTrucklocationById(long id);
    TruckLocation save(TruckLocation trucklocation);
}
