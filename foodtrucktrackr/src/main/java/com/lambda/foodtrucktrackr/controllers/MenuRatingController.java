package com.lambda.foodtrucktrackr.controllers;

import com.lambda.foodtrucktrackr.models.MenuItem;
import com.lambda.foodtrucktrackr.services.MenuRatingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menuratings")
public class MenuRatingController {
    @Autowired
    private MenuRatingService menuRatingService;

//    @ApiOperation(value = "Retrieves a menu rating based off its id", response = MenuItem.class)
//    @GetMapping(value = "/menurating", produces = "application/json")
//    public ResponseEntity<?> findMenuRatingById(
//            @ApiParam(value = )
//    )
}
