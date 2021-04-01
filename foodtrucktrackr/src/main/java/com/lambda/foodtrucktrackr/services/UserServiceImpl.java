package com.lambda.foodtrucktrackr.services;

import com.lambda.foodtrucktrackr.exceptions.ResourceNotFoundException;
import com.lambda.foodtrucktrackr.models.*;
import com.lambda.foodtrucktrackr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    /**
     * Connects this service to the User table.
     */
    @Autowired
    private UserRepository userrepos;

    /**
     * Connects this service to the Role table
     */
    @Autowired
    private RoleService roleService;

    @Autowired
    private TruckService truckService;

    @Autowired
    private MenuRatingService menuRatingService;

    @Autowired
    private TruckRatingService truckRatingService;

    @Autowired
    private HelperFunctions helperFunctions;

    public User findUserById(long id) throws
            ResourceNotFoundException
    {
        return userrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findByNameContaining(String username)
    {
        return userrepos.findByUsernameContainingIgnoreCase(username.toLowerCase());
    }

    @Override
    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        /*
         * findAll returns an iterator set.
         * iterate over the iterator set and add each element to an array list.
         */
        userrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        userrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
        userrepos.deleteById(id);
    }

    @Override
    public User findByName(String name)
    {
        User uu = userrepos.findByUsername(name.toLowerCase());
        if (uu == null)
        {
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return uu;
    }

    @Transactional
    @Override
    public User save(User user)
    {
        User newUser = new User();

        if (user.getUserid() != 0)
        {
            userrepos.findById(user.getUserid())
                    .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found!"));
            newUser.setUserid(user.getUserid());
        }

        newUser.setUsername(user.getUsername()
                .toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setEmail(user.getEmail()
                .toLowerCase());

        newUser.getRoles()
                .clear();
        for (UserRoles ur : user.getRoles())
        {
            Role addRole = roleService.findRoleById(ur.getRole()
                    .getRoleid());
            newUser.getRoles()
                    .add(new UserRoles(newUser,
                            addRole));
        }

        newUser.getMenuratings().clear();
        for (MenuRating mr : user.getMenuratings()) {
            newUser.getMenuratings().add(menuRatingService.findMenuratingById(mr.getMenuratingid()));
        }

//
//        newUser.getUseremails()
//                .clear();
//        for (Useremail ue : user.getUseremails())
//        {
//            newUser.getUseremails()
//                    .add(new Useremail(newUser,
//                            ue.getUseremail()));
//        }

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(
            User user,
            long id)
    {
        User currentUser = findUserById(id);

        if (helperFunctions.isAuthorizedToMakeChange(currentUser.getUsername()))
        {
            if (user.getUsername() != null)
            {
                currentUser.setUsername(user.getUsername()
                        .toLowerCase());
            }

            if (user.getPassword() != null)
            {
                currentUser.setPasswordNoEncrypt(user.getPassword());
            }

            if (user.getEmail() != null)
            {
                currentUser.setEmail(user.getEmail()
                        .toLowerCase());
            }

            if (user.getRoles()
                    .size() > 0)
            {
                currentUser.getRoles()
                        .clear();
                for (UserRoles ur : user.getRoles())
                {
                    Role addRole = roleService.findRoleById(ur.getRole()
                            .getRoleid());

                    currentUser.getRoles()
                            .add(new UserRoles(currentUser,
                                    addRole));
                }
            }
            if (user.getTrucks().size() > 0) {
                currentUser.getTrucks().clear();
                for (UserTrucks ut : user.getTrucks()) {
                    Truck addTruck = truckService.findTruckById(ut.getTruck().getTruckid());
                    currentUser.getTrucks().add(new UserTrucks(currentUser, addTruck));
                }
            }
            if (user.getMenuratings().size() > 0) {
                currentUser.getMenuratings().clear();
                for (MenuRating mr : user.getMenuratings()) {
                    MenuRating addMR = menuRatingService.findMenuratingById(mr.getMenuratingid());
                    currentUser.getMenuratings().add(addMR);
                }
            }
            if (user.getTruckratings().size() > 0) {
                currentUser.getTruckratings().clear();
                for (TruckRating tr : user.getTruckratings()) {
                    TruckRating addTR = truckRatingService.findTruckratingById(tr.getTruckratingid());
                    currentUser.getTruckratings().add(addTR);
                }
            }

//            if (user.getUseremails()
//                    .size() > 0)
//            {
//                currentUser.getUseremails()
//                        .clear();
//                for (Useremail ue : user.getUseremails())
//                {
//                    currentUser.getUseremails()
//                            .add(new Useremail(currentUser,
//                                    ue.getUseremail()));
//                }
//            }

            return userrepos.save(currentUser);
        } else
        {
            // note we should never get to this line but is needed for the compiler
            // to recognize that this exception can be thrown
            throw new ResourceNotFoundException("This user is not authorized to make change");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll()
    {
        userrepos.deleteAll();
    }
}
