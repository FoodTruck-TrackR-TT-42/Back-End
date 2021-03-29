package com.lambda.foodtrucktrackr.services;

import com.lambda.foodtrucktrackr.exceptions.ResourceNotFoundException;
import com.lambda.foodtrucktrackr.models.*;
import com.lambda.foodtrucktrackr.repositories.MenuItemRepository;
import com.lambda.foodtrucktrackr.repositories.MenuRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value = "menuItemService")
public class MenuItemServiceImpl implements MenuItemService {
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private TruckService truckService;

    @Autowired
    private MenuRatingService menuRatingService;

    @Override
    public List<MenuItem> findAll() {
        return null;
    }

//    @Override
//    public List<MenuItem> findMenuItemsByTruckId(long truckid) {
//        return null;
//    }

    @Override
    public MenuItem findMenuitemById(long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item id " + id + " not found!"));
    }

    @Override
    public MenuItem save(MenuItem menuitem) {
        MenuItem newItem = new MenuItem();

        if (menuitem.getMenuitemid() != 0) {
            menuItemRepository.findById(menuitem.getMenuitemid())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item id " + menuitem.getMenuitemid() + " not found!"));
            newItem.setMenuitemid(menuitem.getMenuitemid());
        }

        newItem.setItemname(menuitem.getItemname());
        newItem.setItemprice(menuitem.getItemprice());

        newItem.getMenuitemphotos().clear();
        for (MenuItemPhoto mip : menuitem.getMenuitemphotos()) {
            // when you get to this one, do the same thing as below, with menuItemPhotoService
            newItem.getMenuitemphotos().add(mip);
        }

        newItem.getMenuratings().clear();
        for (MenuRating mr : menuitem.getMenuratings()) {
            newItem.getMenuratings().add(menuRatingService.findMenuratingById(mr.getMenuratingid()));
        }

        newItem.getTrucks().clear();
        for (Menu m : menuitem.getTrucks()) {
            Truck addTruck = truckService.findTruckById(m.getTruck().getTruckid());
            newItem.getTrucks().add(new Menu(addTruck, newItem));
        }

        return menuItemRepository.save(newItem);
    }

    @Override
    public MenuItem update(MenuItem menuitem) {
        return null;
    }
}
