package com.lambda.foodtrucktrackr.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import com.lambda.foodtrucktrackr.models.MenuItem;
import com.lambda.foodtrucktrackr.models.MenuRating;
import com.lambda.foodtrucktrackr.services.MenuRatingService;
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
@RequestMapping("/api/menuratings")
public class MenuRatingController {
    @Autowired
    private MenuRatingService menuRatingService;

    String filter = "menuratingid,score,menuitem[menuitemid,itemname,itemprice],user[userid,username]";
    ObjectMapper objectMapper = Squiggly.init(new ObjectMapper(), filter);

    @ApiOperation(value = "Retrieves a menu rating based off its id", response = MenuItem.class)
    @GetMapping(value = "/menurating/{menuratingid}", produces = "application/json")
    public ResponseEntity<?> findMenuRatingById(
            @ApiParam(value = "menu rating id", required = true, example = "41")
            @PathVariable
            long menuratingid) {
        MenuRating mr = menuRatingService.findMenuratingById(menuratingid);
        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, mr), HttpStatus.OK);
    }


    @ApiOperation(value = "Add a new menu rating", response = MenuRating.class)
    @PostMapping(value = "/menurating/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewMenuRating(
            @Valid
            @RequestBody
            MenuRating newMenuRating) throws URISyntaxException {
        newMenuRating.setMenuratingid(0);
        newMenuRating = menuRatingService.save(newMenuRating);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRatingURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{menuratingid}")
                .buildAndExpand(newMenuRating.getMenuratingid())
                .toUri();
        responseHeaders.setLocation(newRatingURI);

        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, newMenuRating), responseHeaders, HttpStatus.CREATED);
    }
}
