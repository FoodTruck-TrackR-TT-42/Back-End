# Back-End
This is the official Java Back End Repository for FoodTruck TrackR

### BASEURL
https://tt42-foodtrucktrackr.herokuapp.com


### USERS

## Add New User
POST
/api/users/register

## LOGIN
GET
/login

## Get Current User
GET
/api/users/user

## Update User
PUT
/api/users/user/{id}/update

## Return a List of All Users
GET
/api/users/users

##Delete user by id
DELETE
/api/users/user/{id}/delete


### TRUCKS

## Retrieve a list of all trucks
GET
/api/trucks/trucks

## Retrieve a truck based off of it's id
GET
/api/trucks/truck/{truckid}

## Retrieve a list of trucks with the given cuisine type
GET
/api/trucks/cuisinetype/{cuisineType}


### FOR ADDITIONAL ENDPOINTS
Visit: https://tt42-foodtrucktrackr.herokuapp.com/swagger-ui.html