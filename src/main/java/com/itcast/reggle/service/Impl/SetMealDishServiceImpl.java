package com.itcast.reggle.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itcast.reggle.entity.SetmealDish;
import com.itcast.reggle.mapper.SetMealDishMapper;
import com.itcast.reggle.service.SetMealDishService;
import com.itcast.reggle.service.SetMealService;
import org.springframework.stereotype.Service;

@Service
public class SetMealDishServiceImpl extends ServiceImpl<SetMealDishMapper, SetmealDish> implements SetMealDishService {
}
