package com.itcast.reggle.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itcast.reggle.common.CustomExceptionHandler;
import com.itcast.reggle.entity.Category;
import com.itcast.reggle.entity.Dish;
import com.itcast.reggle.entity.Setmeal;
import com.itcast.reggle.mapper.CategoryMapper;
import com.itcast.reggle.mapper.DishMapper;
import com.itcast.reggle.service.CategoryService;
import com.itcast.reggle.service.DishService;
import com.itcast.reggle.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetMealService setMealService;

    /**
     * delete category by id but check if there's any dish involved
     * @param id
     */
    @Override
    public void removeCategoryById(Long id) {
//        select count(*) from dish/setMeal where id = ?
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.eq(Dish::getCategoryId, id);
        int dishCount = dishService.count(dishQueryWrapper);

        LambdaQueryWrapper<Setmeal> setMealQueryWrapper = new LambdaQueryWrapper<>();
        setMealQueryWrapper.eq(Setmeal::getCategoryId, id);
        int setMealCount = setMealService.count(setMealQueryWrapper);
        if (dishCount > 0 || setMealCount > 0) {
//        if any dish or setMeal is involved, then throw "can't be deleted due to..."
            throw new CustomExceptionHandler("Can't be deleted, dish or setMeal involved.");
        }

//        else delete category
        super.removeById(id);
    }
}
