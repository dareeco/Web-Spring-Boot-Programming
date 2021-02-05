package com.example.demo.service;


import com.example.demo.model.MenuItem;

import java.util.List;

public interface MenuItemService{
    MenuItem findById(Long id);

    List<MenuItem> listAll();

    MenuItem create(String name, String description);
}
