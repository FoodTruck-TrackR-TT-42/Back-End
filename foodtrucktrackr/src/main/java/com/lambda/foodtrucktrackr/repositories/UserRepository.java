package com.lambda.foodtrucktrackr.repositories;

import com.lambda.foodtrucktrackr.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

//    List<User> findByUserNameContainingIgnoreCase(String name);
}
