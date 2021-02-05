package com.example.demo.service.impl;

import com.example.demo.model.Menu;
import com.example.demo.model.MenuItem;
import com.example.demo.model.MenuType;
import com.example.demo.model.exceptions.InvalidMenuIdException;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.MenuRepository;
import com.example.demo.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public MenuServiceImpl(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository=menuItemRepository;
    }

    @Override
    public List<Menu> listAll() {
        return menuRepository.findAll();
    }

    @Override
    public Menu findById(Long id) {
        return menuRepository.findById(id).orElseThrow(InvalidMenuIdException::new);
    }

    @Override
    public Menu create(String restaurantName, MenuType menuType, List<Long> menuItems) {
        List<MenuItem> menuItemsIminja=menuItemRepository.findAllById(menuItems);
        Menu menu=new Menu(restaurantName,menuType,menuItemsIminja);
        return menuRepository.save(menu);
    }

    @Override
    public Menu update(Long id, String restaurantName, MenuType menuType, List<Long> menuItems) {
        List<MenuItem> menuItemsIminja=menuItemRepository.findAllById(menuItems);
        Menu menu=this.menuRepository.findById(id).orElseThrow(InvalidMenuIdException::new); //ovaj go bara,se bara i exception
        menu.setRestaurantName(restaurantName);
        menu.setMenuType(menuType);
        menu.setMenuItems(menuItemsIminja);
        return this.menuRepository.save(menu); //this ne zaboravaj
    }

    @Override
    public Menu delete(Long id) {
        Menu menu=this.menuRepository.findById(id).orElseThrow(InvalidMenuIdException::new);
        menuRepository.deleteById(id);
        return menu;
    }

    @Override
    public List<Menu> listMenuItemsByRestaurantNameAndMenuType(String restaurantName, MenuType menuType) {
        String imeRestoran="%"+restaurantName+"%"; //ova ne go zaboravaj, bitno za filtero
        List<Menu> results=null;
        if(restaurantName!=null && menuType!=null){
            return this.menuRepository.findAllByRestaurantNameLikeAndAndMenuType(imeRestoran,menuType);
        }
        else if(restaurantName!=null){
            return this.menuRepository.findAllByRestaurantNameLike(imeRestoran);
        }
        else if(menuType!=null){
            return this.menuRepository.findAllByMenuType(menuType);
        }
        else {
            return  menuRepository.findAll();
        }
    }
}
