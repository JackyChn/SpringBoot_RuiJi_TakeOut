package com.itcast.reggle.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itcast.reggle.common.CustomExceptionHandler;
import com.itcast.reggle.dto.SetmealDto;
import com.itcast.reggle.entity.Setmeal;
import com.itcast.reggle.entity.SetmealDish;
import com.itcast.reggle.mapper.SetMealMapper;
import com.itcast.reggle.service.SetMealDishService;
import com.itcast.reggle.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {
    @Autowired
    private SetMealDishService setMealDishService;

    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
//        save setmeal basic data
        this.save(setmealDto);

//        save setmeal dish
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setMealDishService.saveBatch(setmealDishes);
    }

    @Transactional
    public void removeWithDish(List<Long> ids) {
//        check dish status if it can be removed, select count(*) from setmeal where id in ids and status = 1;
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);
        int count = this.count(queryWrapper);

//        no then throw exception
        if(count > 0) {
            throw new CustomExceptionHandler("Setmeal status is on sale, plz ban it first");
        }

//        yes then delete data in setmeal table, count == 0
        this.removeByIds(ids);

//        then delete data in setmeal_dish table, delete from setmeal_dish where setmeal_id in ids
        LambdaQueryWrapper<SetmealDish> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(SetmealDish::getSetmealId, ids);
        setMealDishService.remove(queryWrapper1);
    }

}
