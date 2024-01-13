package com.itcast.reggle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itcast.reggle.dto.SetmealDto;
import com.itcast.reggle.entity.Setmeal;

import java.util.List;

public interface SetMealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);

}
