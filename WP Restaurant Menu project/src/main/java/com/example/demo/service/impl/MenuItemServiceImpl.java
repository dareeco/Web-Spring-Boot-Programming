package com.example.demo.service.impl;

import com.example.demo.model.MenuItem;
import com.example.demo.model.exceptions.InvalidMenuItemIdException;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.MenuRepository;
import com.example.demo.service.MenuItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id).orElseThrow(InvalidMenuItemIdException::new);
    }

    @Override
    public List<MenuItem> listAll() {
        return menuItemRepository.findAll();
    }

    @Override
    public MenuItem create(String name, String description) {
        MenuItem menuItem=new MenuItem(name,description);
        return menuItemRepository.save(menuItem);
    }
}
