package com.lambda.foodtrucktrackr.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import com.lambda.foodtrucktrackr.models.ErrorDetail;
import com.lambda.foodtrucktrackr.models.User;
import com.lambda.foodtrucktrackr.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    String filter = "userid,username,email,roles[role[name]],trucks[truck[truckid,truckname]],menuratings[menuratingid,score,menuitem[menuitemid]],truckratings[truckratingid,score,truck[truckid]]";
    ObjectMapper objectMapper = Squiggly.init(new ObjectMapper(), filter);

    /**
     * Returns a list of all users
     * <br>Example: <a href="http://localhost:2019/users/users">http://localhost:2019/users/users</a>
     *
     * @return JSON list of all users with a status of OK
     * @see UserService#findAll() UserService.findAll()
     */
    @ApiOperation(value = "Returns a list of all Users", response = User.class, responseContainer = "List")
    @GetMapping(value = "/users",
            produces = "application/json")
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, myUsers), HttpStatus.OK);
    }

    /**
     * Returns a single user based off a user id number
     * <br>Example: http://localhost:2019/users/user/7
     *
     * @param userId The primary key of the user you seek
     * @return JSON object of the user you seek
     * @see UserService#findUserById(long) UserService.findUserById(long)
     */
    @ApiOperation(value = "Retrieves a user based off its user id", response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "User found", response = User.class),
                        @ApiResponse(code = 404, message = "User not found", response = ErrorDetail.class)})
    @GetMapping(value = "/user/{userId}",
            produces = "application/json")
    public ResponseEntity<?> getUserById(
            @ApiParam(value = "user id", required = true, example = "4")
            @PathVariable
            Long userId)
    {
        User u = userService.findUserById(userId);
        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, u),
                HttpStatus.OK);
    }

    /**
     * Return a user object based on a given username
     * <br>Example: <a href="http://localhost:2019/users/user/name/cinnamon">http://localhost:2019/users/user/name/cinnamon</a>
     *
     * @param userName the name of user (String) you seek
     * @return JSON object of the user you seek
     * @see UserService#findByName(String) UserService.findByName(String)
     */
    @GetMapping(value = "/user/name/{userName}",
            produces = "application/json")
    public ResponseEntity<?> getUserByName(
            @PathVariable
            String userName)
    {
        User u = userService.findByName(userName);
        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, u),
                HttpStatus.OK);
    }

    /**
     * Returns a list of users whose username contains the given substring
     * <br>Example: <a href="http://localhost:2019/users/user/name/like/da">http://localhost:2019/users/user/name/like/da</a>
     *
     * @param userName Substring of the username for which you seek
     * @return A JSON list of users you seek
     * @see UserService#findByNameContaining(String) UserService.findByNameContaining(String)
     */
    @GetMapping(value = "/user/name/like/{userName}",
            produces = "application/json")
    public ResponseEntity<?> getUserLikeName(
            @PathVariable
            String userName)
    {
        List<User> u = userService.findByNameContaining(userName);
        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, u),
                HttpStatus.OK);
    }

    /**
     * Given a complete User Object, create a new User record and accompanying useremail records
     * and user role records.
     * <br> Example: <a href="http://localhost:2019/users/user">http://localhost:2019/users/user</a>
     *
     * @param newuser A complete new user to add including emails and roles.
     *                roles must already exist.
     * @return A location header with the URI to the newly created user and a status of CREATED
     * @throws URISyntaxException Exception if something does not work in creating the location header
     * @see UserService#save(User) UserService.save(User)
     */
//    @PostMapping(value = "/user/register",
//            consumes = "application/json")
//    public ResponseEntity<?> addNewUser(
//            @Valid
//            @RequestBody
//            User newuser) throws
//            URISyntaxException
//    {
//        newuser.setUserid(0);
//        newuser = userService.save(newuser);
//
//        // set the location header for the newly created resource
//        HttpHeaders responseHeaders = new HttpHeaders();
//        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{userid}")
//                .buildAndExpand(newuser.getUserid())
//                .toUri();
//        responseHeaders.setLocation(newUserURI);
//
//        return new ResponseEntity<>(null,
//                responseHeaders,
//                HttpStatus.CREATED);
//    }

    /**
     * Given a complete User Object
     * Given the user id, primary key, is in the User table,
     * replace the User record and Useremail records.
     * Roles are handled through different endpoints
     * <br> Example: <a href="http://localhost:2019/users/user/15">http://localhost:2019/users/user/15</a>
     *
     * @param updateUser A complete User including all emails and roles to be used to
     *                   replace the User. Roles must already exist.
     * @param userid     The primary key of the user you wish to replace.
     * @return status of OK
     * @see UserService#save(User) UserService.save(User)
     */
    @PutMapping(value = "/user/{userid}/update",
            consumes = "application/json")
    public ResponseEntity<?> updateFullUser(
            @Valid
            @RequestBody
            User updateUser,
            @PathVariable
            long userid)
    {
        updateUser.setUserid(userid);
        userService.save(updateUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Updates the user record associated with the given id with the provided data. Only the provided fields are affected.
     * Roles are handled through different endpoints
     * If an email list is given, it replaces the original emai list.
     * <br> Example: <a href="http://localhost:2019/users/user/7">http://localhost:2019/users/user/7</a>
     *
     * @param updateUser An object containing values for just the fields that are being updated. All other fields are left NULL.
     * @param id         The primary key of the user you wish to update.
     * @return A status of OK
     * @see UserService#update(User, long) UserService.update(User, long)
     */
    @PatchMapping(value = "/user/{id}/update",
            consumes = "application/json")
    public ResponseEntity<?> updateUser(
            @RequestBody
            User updateUser,
            @PathVariable
            long id)
    {
        userService.update(updateUser,
                id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a given user along with associated emails and roles
     * <br>Example: <a href="http://localhost:2019/users/user/14">http://localhost:2019/users/user/14</a>
     *
     * @param id the primary key of the user you wish to delete
     * @return Status of OK
     */
    @DeleteMapping(value = "/user/{id}/delete")
    public ResponseEntity<?> deleteUserById(
            @PathVariable
            long id)
    {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Returns the User record for the currently authenticated user based off of the supplied access token
     * <br>Example: <a href="http://localhost:2019/users/getuserinfo">http://localhost:2019/users/getuserinfo</a>
     *
     * @param authentication The authenticated user object provided by Spring Security
     * @return JSON of the current user. Status of OK
     * @see UserService#findByName(String) UserService.findByName(authenticated user)
     */
    @ApiOperation(value = "returns the currently authenticated user",
            response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "User found", response = User.class),
            @ApiResponse(code = 404, message = "User not found", response = ErrorDetail.class)})
    @GetMapping(value = "/user",
            produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication)
    {
        User u = userService.findByName(authentication.getName());
        return new ResponseEntity<>(SquigglyUtils.objectify(objectMapper, u),
                HttpStatus.OK);
    }


}
