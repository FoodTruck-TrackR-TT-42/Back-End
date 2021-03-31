package com.lambda.foodtrucktrackr.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import com.lambda.foodtrucktrackr.models.ErrorDetail;
import com.lambda.foodtrucktrackr.models.Truck;
import com.lambda.foodtrucktrackr.services.TruckService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/trucks")
public class TruckController {
    @Autowired
    private TruckService truckService;

    String filter = "truckid,truckname,cuisinetype,menus[menuitem[menuitemid,itemname,itemprice]],users[user[userid]],truckratings[truckratingid,score,user[userid]]";
    ObjectMapper objectMapper = Squiggly.init(new ObjectMapper(), filter);

    @ApiOperation(value = "Returns a list of all Trucks", response = Truck.class, responseContainer = "List")
    @GetMapping(value = "/trucks",
            produces = "application/json")
    public ResponseEntity<?> listAllTrucks() {
//        String filter = "truckid,truckname,cuisinetype,menus[menuitem[menuitemid,itemname,itemprice]],users[user[userid]],truckratings[truckrating[truckratingid,score]]";
//        ObjectMapper objectMapper = Squiggly.init(new ObjectMapper(), filter);
        List<Truck> trucks = truckService.findAll();
        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, trucks), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves a truck based off its truck id", response = Truck.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Truck found", response = Truck.class),
                            @ApiResponse(code = 404, message = "Truck not found", response = ErrorDetail.class)})
    @GetMapping(value = "/truck/{truckid}", produces = "application/json")
    public ResponseEntity<?> getTruckById(
            @ApiParam(value = "truck id", required = true, example = "19")
            @PathVariable
            long truckid) {
//        String filter = "truckid,truckname,cuisinetype,menus[menuitem[menuitemid,itemname,itemprice]],users[user[userid]],truckratings[truckrating[truckratingid,score]]";
//        ObjectMapper objectMapper = Squiggly.init(new ObjectMapper(), filter);
        Truck t = truckService.findTruckById(truckid);
        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, t), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves a list of trucks with the given cuisineType", response = Truck.class, responseContainer = "List")
    @GetMapping(value = "/cuisinetype/{cuisineType}", produces = "application/json")
    public ResponseEntity<?> getTrucksByCuisineType(
            @ApiParam(value = "cuisine type", required = true, example = "Salvadoran")
            @PathVariable
            String cuisineType) {
//        String filter = "truckid,truckname,cuisinetype,menus[menuitem[menuitemid,itemname,itemprice]],users[user[userid]],truckratings[truckrating[truckratingid,score]]";
//        ObjectMapper objectMapper = Squiggly.init(new ObjectMapper(), filter);
        List<Truck> trucks = truckService.findTrucksByCuisineType(cuisineType);
        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, trucks), HttpStatus.OK);
    }

    @ApiOperation(value = "Adds a new truck to the database", response = Truck.class)
    @PostMapping(value = "/truck/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewTruck(@Valid @RequestBody Truck newtruck) throws URISyntaxException {
        newtruck.setTruckid(0);
        newtruck = truckService.save(newtruck);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newTruckURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{truckid}")
                .buildAndExpand(newtruck.getTruckid())
                .toUri();
        responseHeaders.setLocation(newTruckURI);

        return new ResponseEntity<>(newtruck, responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Updates an existing truck", response = Truck.class)
    @PutMapping(value = "/truck/{truckid}/update",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> updateTruck(
            @Valid @RequestBody Truck updatedTruck,
            @PathVariable long truckid) {
        updatedTruck.setTruckid(truckid);
        updatedTruck = truckService.save(updatedTruck);

        return new ResponseEntity<>(updatedTruck, HttpStatus.OK);
    }
}
