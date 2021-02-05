package com.example.demo.repository;

import com.example.demo.model.Menu;
import com.example.demo.model.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    List<Menu> findAllByRestaurantNameLikeAndAndMenuType(String name, MenuType menuType);
    List<Menu> findAllByRestaurantNameLike(String name);
    List<Menu> findAllByMenuType(MenuType menuType);

}
