package com.lambda.foodtrucktrackr.services;

import com.lambda.foodtrucktrackr.models.MenuItem;
import com.lambda.foodtrucktrackr.repositories.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value = "menuItemService")
public class MenuItemServiceImpl implements MenuItemService {
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public List<MenuItem> findAll() {
        return null;
    }

    @Override
    public List<MenuItem> findMenuItemsByTruckId(long truckid) {
        return null;
    }

    @Override
    public MenuItem findMenuitemById(long id) {
        return null;
    }

    @Override
    public MenuItem save(MenuItem menuitem) {
        return null;
    }

    @Override
    public MenuItem update(MenuItem menuitem) {
        return null;
    }
}
