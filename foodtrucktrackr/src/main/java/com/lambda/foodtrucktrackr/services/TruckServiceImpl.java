package com.lambda.foodtrucktrackr.services;

import com.lambda.foodtrucktrackr.exceptions.ResourceNotFoundException;
import com.lambda.foodtrucktrackr.models.*;
import com.lambda.foodtrucktrackr.repositories.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "truckService")
public class TruckServiceImpl  implements TruckService {
    @Autowired
    private TruckRepository truckrepos;

    @Autowired
    private UserService userService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private TruckRatingService truckRatingService;

    @Override
    public List<Truck> findAll() {
        List<Truck> trucks = new ArrayList<>();

        truckrepos.findAll()
                .iterator()
                .forEachRemaining(trucks::add);

        return trucks;
    }

    @Override
    public Truck findTruckById(long id) throws ResourceNotFoundException {
        return truckrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Truck id " + id + " not found!"));
    }

//    @Override
//    public List<Truck> findTrucksByUserId(long id) {
//
//        return null;
//    }

    @Override
    public List<Truck> findTrucksByCuisineType(String cuisineType) {
        List<Truck> trucks = truckrepos.findByCuisinetypeContainingIgnoringCase(cuisineType);
        return trucks;
    }

    @Transactional
    @Override
    public Truck save(Truck truck) {
        Truck newTruck = new Truck();

        if (truck.getTruckid() != 0) {
            truckrepos.findById(truck.getTruckid())
                .orElseThrow(() -> new ResourceNotFoundException("Truck id " + truck.getTruckid() + " not found!"));
            newTruck.setTruckid(truck.getTruckid());
        }

        newTruck.setTruckname(truck.getTruckname());
        newTruck.setCuisinetype(truck.getCuisinetype());

        newTruck.getMenus().clear();
        for (Menu m : truck.getMenus()) {
            MenuItem addItem = menuItemService.findMenuitemById(m.getMenuitem()
                    .getMenuitemid());
            newTruck.getMenus()
                    .add(new Menu(newTruck, addItem));
        }

        newTruck.getUsers().clear();
        for (UserTrucks ut : truck.getUsers()) {
            User addUser = userService.findUserById(ut.getUser()
                    .getUserid());
            newTruck.getUsers().add(new UserTrucks(addUser, newTruck));
        }

        newTruck.getTruckratings().clear();
        for (TruckRating tr : truck.getTruckratings()) {
            newTruck.getTruckratings().add(truckRatingService.findTruckratingById(tr.getTruckratingid()));
        }

        return truckrepos.save(newTruck);
    }

    @Override
    public Truck update(Truck truck, long id) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll() { truckrepos.deleteAll(); }
}
