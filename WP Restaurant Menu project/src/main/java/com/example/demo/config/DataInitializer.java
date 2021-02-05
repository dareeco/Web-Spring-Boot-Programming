package com.example.demo.config;

import com.example.demo.model.MenuType;
import com.example.demo.service.MenuItemService;
import com.example.demo.service.MenuService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataInitializer {

    public static final String ADMIN = "admin";


    private final MenuItemService menuItemService;
    private final MenuService menuService;

    public DataInitializer(MenuItemService menuItemService, MenuService menuService) {
        this.menuItemService = menuItemService;
        this.menuService = menuService;
    }

    @PostConstruct
    public void initData() {
        for (int i = 1; i < 6; i++) {
            this.menuItemService.create("Food " + i, "Food " + i + " description");
        }

        for (int i = 1; i < 11; i++) {
            this.menuService.create("Restaurant " + i,
                    MenuType.values()[i % MenuType.values().length],
                    Stream.of(1L, i % 5L + 1).collect(Collectors.toList()));
        }
    }
}
