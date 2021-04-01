package com.lambda.foodtrucktrackr.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import com.lambda.foodtrucktrackr.models.TruckLocation;
import com.lambda.foodtrucktrackr.services.TruckLocationService;
import com.lambda.foodtrucktrackr.services.TruckService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/trucklocation")
public class TruckLocationController {
    @Autowired
    private TruckLocationService truckLocationService;

    @Autowired
    private TruckService truckService;

    String filter = "trucklocationid,latitude,longitude,departuretime,truck[truckid]";
    ObjectMapper objectMapper = Squiggly.init(new ObjectMapper(), filter);

    @ApiOperation(value = "Updates a truck's location", response = TruckLocation.class)
    @PutMapping(value = "/{truckid}/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateTruckLocation(
            @Valid @RequestBody TruckLocation updatedLocation,
            @PathVariable long truckid) {
        updatedLocation.setTrucklocationid(truckService.findTruckById(truckid).getCurrentlocation().getTrucklocationid());
        updatedLocation = truckLocationService.save(updatedLocation);

        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, updatedLocation), HttpStatus.OK);
    }
}
