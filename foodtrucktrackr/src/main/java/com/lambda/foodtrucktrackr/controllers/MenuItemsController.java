package com.lambda.foodtrucktrackr.controllers;

import com.lambda.foodtrucktrackr.models.MenuItem;
import com.lambda.foodtrucktrackr.services.MenuItemService;
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
@RequestMapping("/api/menus")
public class MenuItemsController {
    @Autowired
    private MenuItemService menuItemService;

    @ApiOperation(value = "Retrieves a menu item based off its id", response = MenuItem.class)
    @GetMapping(value = "/menuitem/{menuitemid}", produces = "application/json")
    public ResponseEntity<?> findMenuItemById(
            @ApiParam(value = "menu item id", required = true, example = "37")
            @PathVariable
            long menuitemid) {
        MenuItem mi = menuItemService.findMenuitemById(menuitemid);
        return new ResponseEntity<>(mi, HttpStatus.OK);
    }

    @ApiOperation(value = "Add a new menu item", response = MenuItem.class)
    @PostMapping(value = "/menuitem/add", produces = "application/json")
    public ResponseEntity<?> addNewMenuItem(
            @Valid
            @RequestBody
            MenuItem newMenuItem) throws URISyntaxException {
        newMenuItem.setMenuitemid(0);
        newMenuItem = menuItemService.save(newMenuItem);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newMenuURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{menuitemid}")
                .buildAndExpand(newMenuItem.getMenuitemid())
                .toUri();
        responseHeaders.setLocation(newMenuURI);

        return new ResponseEntity<>(newMenuItem, responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Updates an existing menu item", response = MenuItem.class)
    @PutMapping(value = "/menuitem/{menuitemid}/update",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> updateMenuItem(
            @Valid @RequestBody MenuItem updatedMenuItem,
            @PathVariable long menuitemid) {
        updatedMenuItem.setMenuitemid(menuitemid);
        updatedMenuItem = menuItemService.save(updatedMenuItem);

        return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
    }
}
