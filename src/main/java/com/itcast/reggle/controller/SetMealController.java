package com.itcast.reggle.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itcast.reggle.common.R;
import com.itcast.reggle.dto.DishDto;
import com.itcast.reggle.dto.SetmealDto;
import com.itcast.reggle.entity.Category;
import com.itcast.reggle.entity.Setmeal;
import com.itcast.reggle.entity.SetmealDish;
import com.itcast.reggle.service.CategoryService;
import com.itcast.reggle.service.SetMealDishService;
import com.itcast.reggle.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    @Autowired
    private SetMealDishService setMealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * new setMeal
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setMealService.saveWithDish(setmealDto);
        return R.success("New setMeal added!");
    }

    /**
     * page query
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> pageQuery(int page, int pageSize, String name) {
        Page<Setmeal> setmealPageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(name), Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setMealService.page(setmealPageInfo, queryWrapper);

        BeanUtils.copyProperties(setmealPageInfo, setmealDtoPageInfo, "records");

        List<Setmeal> records = setmealPageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }

            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPageInfo.setRecords(list);
        return R.success(setmealDtoPageInfo);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        setMealService.removeWithDish(ids);
        return R.success("Setmeal deleted!");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setMealService.list(queryWrapper);

        return R.success(list);
    }
}
