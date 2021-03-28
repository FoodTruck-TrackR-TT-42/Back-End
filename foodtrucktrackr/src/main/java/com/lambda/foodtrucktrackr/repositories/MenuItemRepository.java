package com.lambda.foodtrucktrackr.repositories;

import com.lambda.foodtrucktrackr.models.MenuItem;
import org.springframework.data.repository.CrudRepository;

public interface MenuItemRepository extends CrudRepository<MenuItem, Long> {
}
