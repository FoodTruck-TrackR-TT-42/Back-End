package com.lambda.foodtrucktrackr.controllers;

import com.lambda.foodtrucktrackr.models.Truck;
import com.lambda.foodtrucktrackr.services.TruckService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trucks")
public class TruckController {
    @Autowired
    private TruckService truckService;

    @ApiOperation(value = "Returns a list of all Trucks", response = Truck.class, responseContainer = "List")
    @GetMapping(value = "/trucks",
            produces = "application/json")
    public ResponseEntity<?> listAllTrucks() {
        List<Truck> trucks = truckService.findAll();
        return new ResponseEntity<>(trucks, HttpStatus.OK);
    }
}
