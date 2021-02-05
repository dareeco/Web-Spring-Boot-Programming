package com.example.demo.web;

import com.example.demo.model.Menu;
import com.example.demo.model.MenuItem;
import com.example.demo.model.MenuType;
import com.example.demo.service.MenuItemService;
import com.example.demo.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuController {

    private final MenuService service;
    private final MenuItemService menuItemService;

    public MenuController(MenuService service,MenuItemService menuItemService) {
        this.service = service;
        this.menuItemService=menuItemService;
    }
    @GetMapping({"/","/menu"})
    public String showMenus(@RequestParam(required = false) String nameSearch, @RequestParam(required = false) MenuType menuType, Model model) {
        List<Menu> menus;
        List<MenuType> menuTypes=new ArrayList<>(); //mora aaray list oti inache nema da fakja
        //od html-o gledame deka to ni se bara
        if (nameSearch == null && menuType == null) {
            menus=service.listAll();
        } else {
            menus=this.service.listMenuItemsByRestaurantNameAndMenuType(nameSearch,  menuType);
        }
        model.addAttribute("menus",menus);
        menuTypes.add(MenuType.COFFE);
        menuTypes.add(MenuType.PIZZA);
        menuTypes.add(MenuType.COOKIE); //MORA RACHNO DODAVANJE OTI NEMA ZA NIV SERVICE

        model.addAttribute("menuTypes",menuTypes);
        return "list.html";
    }

    @GetMapping("/menu/add") //add e so GET,pamti
    public String showAdd(Model model) {
        List<MenuType> menuTypes=new ArrayList<>();
        menuTypes.add(MenuType.COFFE);
        menuTypes.add(MenuType.PIZZA);
        menuTypes.add(MenuType.COOKIE);
        model.addAttribute("menuTypes",menuTypes);

        List<MenuItem> menuItems=this.menuItemService.listAll(); //site zemi gi i dodaj gi za da gi ima ko opcii za dodavanje
        model.addAttribute("menuItems",menuItems);


        return "form.html";
    }

    @GetMapping("/menu/{id}/edit") //so aglesti ne so golemi zagradi
    public String showEdit(@PathVariable Long id,Model model ){
        Menu menu=this.service.findById(id);

        List<MenuType> menuTypes=new ArrayList<>();
        menuTypes.add(MenuType.COFFE);
        menuTypes.add(MenuType.PIZZA);
        menuTypes.add(MenuType.COOKIE);

        List<MenuItem> menuItems=menuItemService.listAll();

        model.addAttribute("menu",menu);
        model.addAttribute("menuItems",menuItems);
        model.addAttribute("menuTypes",menuTypes);


        return "form.html";
    }

    @PostMapping("/menu")
    public String create( @RequestParam String name,
                          @RequestParam  MenuType menuType,
                          @RequestParam  List<Long> menuItemIds) {
        this.service.create(name,menuType,menuItemIds);
        return "redirect:/menu";
    }

    @PostMapping("/menu/{id}")  //so aglesti ne so golemi zagradi
    public String update( @PathVariable Long id,
                          @RequestParam String name,
            /*@RequestParam String description,*/
                          @RequestParam MenuType menuType,
                          @RequestParam List<Long> menuItemIds) {
        this.service.update(id,name,menuType,menuItemIds);
        return "redirect:/menu";
    }
    @PostMapping("/menu/{id}/delete")
    public String delete(@PathVariable Long id) {
        this.service.delete(id);
        return "redirect:/menu";
    }
}
