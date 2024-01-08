package com.itcast.reggle.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itcast.reggle.entity.Dish;
import com.itcast.reggle.mapper.DishMapper;
import com.itcast.reggle.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
