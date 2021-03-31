# Back-End
This is the official Java Back End Repository for FoodTruck TrackR

### BASEURL
https://tt42-foodtrucktrackr.herokuapp.com


## USERS

### Add New User
POST
/api/users/user/register

### Login
GET
/login

### Get Current User
GET
/api/users/user

### Update User
PUT
/api/users/user/{id}/update

### Return a List of All Users
GET
/api/users/users

### Delete user by id
DELETE
/api/users/user/{id}/delete


## TRUCKS

### Retrieve a list of all trucks
GET
/api/trucks/trucks

### Retrieve a truck based off of its id
GET
/api/trucks/truck/{truckid}

### Add A new Truck
POST
/api/trucks/truck/add

### Update Existing User
PUT
/api/trucks/truck/{truckid}/update

### Retrieve a list of trucks with the given cuisine type
GET
/api/trucks/cuisinetype/{cuisineType}

## MENU ITEMS

### Add New Menu Item
POST
/api/menus/menuitem/add

### Retrieve a Menu Item based off of its id
GET
/api/menus/menuitem/{menuitemid}

### Update an existing menu item
PUT
/api/menus/menuitem/{menuitemid}/update


## FOR ADDITIONAL INFO ON THESE ENDPOINTS
Visit: https://tt42-foodtrucktrackr.herokuapp.com/swagger-ui.html
