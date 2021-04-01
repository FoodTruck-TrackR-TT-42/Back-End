package com.lambda.foodtrucktrackr.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import com.lambda.foodtrucktrackr.handlers.FileUploadUtil;
import com.lambda.foodtrucktrackr.models.MenuItem;
import com.lambda.foodtrucktrackr.services.MenuItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/menus")
public class MenuItemsController {
    @Autowired
    private MenuItemService menuItemService;

    String filter = "menuitemid,itemname,itemprice,trucks[truck[truckid,truckname]],menuitemphotos[menitemphotoid,imageurl],menuratings[menuratingid,score,user[userid,username]]";
    ObjectMapper objectMapper = Squiggly.init(new ObjectMapper(), filter);

    @ApiOperation(value = "Retrieves a menu item based off its id", response = MenuItem.class)
    @GetMapping(value = "/menuitem/{menuitemid}", produces = "application/json")
    public ResponseEntity<?> findMenuItemById(
            @ApiParam(value = "menu item id", required = true, example = "37")
            @PathVariable
            long menuitemid) {
        MenuItem mi = menuItemService.findMenuitemById(menuitemid);
        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, mi), HttpStatus.OK);
    }

    @ApiOperation(value = "menu item photo")
    @PostMapping("/menuitem/{menuitemid}/add/image")
    public RedirectView saveMenuItemPhoto (MenuItem menuItem,
                                           @RequestParam ("image")MultipartFile multipartFile) throws IOException {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        menuItem.setMenuitemphotos(filename);

        MenuItem savedMenuItem = menuItemService.save(menuItem);
        String uploadDir = "menuitem-photos/" + savedMenuItem.getMenuitemid();
        FileUploadUtil.saveFile(uploadDir, filename, multipartFile);

        return new RedirectView("/menuitem/{menuitemid}", true);
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

        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, newMenuItem), responseHeaders, HttpStatus.CREATED);
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

        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, updatedMenuItem), HttpStatus.OK);
    }
}
