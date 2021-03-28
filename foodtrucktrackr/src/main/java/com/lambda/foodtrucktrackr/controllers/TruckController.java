package com.lambda.foodtrucktrackr.controllers;

import com.lambda.foodtrucktrackr.models.ErrorDetail;
import com.lambda.foodtrucktrackr.models.Truck;
import com.lambda.foodtrucktrackr.services.TruckService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @ApiOperation(value = "Retrieves a truck based off its truck id", response = Truck.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Truck found", response = Truck.class),
                            @ApiResponse(code = 404, message = "Truck not found", response = ErrorDetail.class)})
    @GetMapping(value = "/truck/{truckid}", produces = "application/json")
    public ResponseEntity<?> getTruckById(
            @ApiParam(value = "truck id", required = true, example = "19")
            @PathVariable
            Long truckid) {
        Truck t = truckService.findTruckById(truckid);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves a list of trucks with the given cuisineType", response = Truck.class, responseContainer = "List")
    @GetMapping(value = "/cuisinetype/{cuisineType}", produces = "application/json")
    public ResponseEntity<?> getTrucksByCuisineType(
            @ApiParam(value = "cuisine type", required = true, example = "Salvadoran")
            @PathVariable
            String cuisineType) {
        List<Truck> trucks = truckService.findTrucksByCuisineType(cuisineType);
        return new ResponseEntity<>(trucks, HttpStatus.OK);
    }
}
