package com.lambda.foodtrucktrackr;

import com.lambda.foodtrucktrackr.models.*;
import com.lambda.foodtrucktrackr.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@ConditionalOnProperty(prefix = "command.line.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
public class SeedData implements CommandLineRunner {
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    RoleService roleService;

    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;

    @Autowired
    TruckService truckService;

    @Autowired
    MenuItemService menuItemService;

    @Autowired
    MenuRatingService menuRatingService;

    @Autowired
    TruckRatingService truckRatingService;

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args) throws
            Exception
    {
        // define roles
        userService.deleteAll();
        roleService.deleteAll();
        truckService.deleteAll();
        Role r1 = new Role("operator");
        Role r2 = new Role("diner");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);

        // operator
        User u1 = new User("Sam",
                "password",
                "sam@lambdaschool.local");
        u1.getRoles()
                .add(new UserRoles(u1,
                        r1));
        u1 = userService.save(u1);

        // diner
        User u2 = new User("Beau",
                "password",
                "beau@lambdaschool.local");
        u2.getRoles()
                .add(new UserRoles(u2,
                        r2));
        u2 = userService.save(u2);

        // menuitems
        MenuItem mi1 = new MenuItem("BLT", "Thick-cut, applewood-smoked bacon with Bibb lettuce and sliced tomato", 7.99);
//        mi1.getMenuratings().add(mr1);
        mi1 = menuItemService.save(mi1);
//        mr1.setMenuitem(mi1);
//        mr1 = menuRatingService.save(mr1);

        // menurating
        MenuRating mr1 = new MenuRating(4, mi1, u2);
//        mr1.setUser(u2);
//        u2.getMenuratings().add(mr1);
        mr1 = menuRatingService.save(mr1);

        // truck
        Truck t1 = new Truck("Lunch Box", "Sandwiches");
        t1.getMenus().add(new Menu(t1, mi1));
        t1.getUsers().add(new UserTrucks(u1, t1));
//        t1.getTruckratings().add(tr1);
        t1 = truckService.save(t1);
//        tr1.setTruck(t1);
//        tr1 = truckRatingService.save(tr1);

        // truckrating
        TruckRating tr1 = new TruckRating(5, t1, u2);
        tr1 = truckRatingService.save(tr1);
    }
}
