package com.lambda.foodtrucktrackr.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import com.lambda.foodtrucktrackr.models.TruckRating;
import com.lambda.foodtrucktrackr.services.TruckRatingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/truckratings")
public class TruckRatingController {
    @Autowired
    private TruckRatingService truckRatingService;

    String filter = "truckratingid,score,truck[truckid,truckname],user[userid,username]";
    ObjectMapper objectMapper = Squiggly.init(new ObjectMapper(), filter);

    @ApiOperation(value = "Retrieves a truck rating based off its id", response = TruckRating.class)
    @GetMapping(value = "/truckrating/{truckratingid}", produces = "application/json")
    public ResponseEntity<?> findTruckRatingById(
            @ApiParam(value = "truck rating id", required = true, example = "43")
            @PathVariable
            long truckratingid) {
        TruckRating tr = truckRatingService.findTruckratingById(truckratingid);
        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, tr), HttpStatus.OK);
    }

    @ApiOperation(value = "Add a new truck rating", response = TruckRating.class)
    @PostMapping(value = "/truckrating/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewTruckRating(
            @Valid
            @RequestBody
            TruckRating newTruckRating) throws URISyntaxException {
        newTruckRating.setTruckratingid(0);
        newTruckRating = truckRatingService.save(newTruckRating);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRatingURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{truckratingid}")
                .buildAndExpand(newTruckRating.getTruckratingid())
                .toUri();
        responseHeaders.setLocation(newRatingURI);

        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, newTruckRating), responseHeaders, HttpStatus.CREATED);
    }
}
