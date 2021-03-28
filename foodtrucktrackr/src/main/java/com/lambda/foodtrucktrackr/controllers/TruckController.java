package com.lambda.foodtrucktrackr.controllers;

import com.lambda.foodtrucktrackr.services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trucks")
public class TruckController {
    @Autowired
    private TruckService truckService;
}
