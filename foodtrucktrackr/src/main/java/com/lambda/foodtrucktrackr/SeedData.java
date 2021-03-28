package com.lambda.foodtrucktrackr;

import com.lambda.foodtrucktrackr.models.*;
import com.lambda.foodtrucktrackr.services.RoleService;
import com.lambda.foodtrucktrackr.services.TruckService;
import com.lambda.foodtrucktrackr.services.UserService;
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
//        u1.getUseremails()
//                .add(new Useremail(u1,
//                        "admin@email.local"));
//        u1.getUseremails()
//                .add(new Useremail(u1,
//                        "admin@mymail.local"));
        userService.save(u1);

        // diner
        User u2 = new User("Beau",
                "password",
                "beau@lambdaschool.local");
        u2.getRoles()
                .add(new UserRoles(u2,
                        r2));
//        u2.getUseremails()
//                .add(new Useremail(u2,
//                        "cinnamon@mymail.local"));
//        u2.getUseremails()
//                .add(new Useremail(u2,
//                        "hops@mymail.local"));
//        u2.getUseremails()
//                .add(new Useremail(u2,
//                        "bunny@email.local"));
        userService.save(u2);

        // truck

        Truck t1 = new Truck("Lunch Box", "Sandwiches");
        truckService.save(t1);
    }
}
