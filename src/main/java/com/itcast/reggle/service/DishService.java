package com.itcast.reggle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itcast.reggle.dto.DishDto;
import com.itcast.reggle.entity.Dish;
import com.itcast.reggle.entity.DishFlavor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DishService extends IService<Dish> {
//    save new dish to its table and its corresponding flavor to dish_flavor table
    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);
}
