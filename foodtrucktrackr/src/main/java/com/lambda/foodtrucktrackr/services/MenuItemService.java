package com.lambda.foodtrucktrackr.services;

import com.lambda.foodtrucktrackr.models.MenuItem;

import java.util.List;

public interface MenuItemService {
    List<MenuItem> findAll();
//    List<MenuItem> findMenuItemsByTruckId(long truckid);
    MenuItem findMenuitemById(long id);
    MenuItem save(MenuItem menuitem);
    MenuItem update(MenuItem menuitem);
}
