package com.itcast.reggle.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itcast.reggle.common.R;
import com.itcast.reggle.dto.DishDto;
import com.itcast.reggle.entity.Category;
import com.itcast.reggle.entity.Dish;
import com.itcast.reggle.service.CategoryService;
import com.itcast.reggle.service.DishFlavorService;
import com.itcast.reggle.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("new dish added!");
    }

    @GetMapping("/page")
    public R<Page<DishDto>> pageQuery(int page, int pageSize, String name) {
        Page<Dish> dishPageInfo = new Page<>(page, pageSize);  // categoryId
        Page<DishDto> dishDtoPageInfo = new Page<>(page, pageSize);  // ready to change categoryId to categoryName using dto

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(name), Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(dishPageInfo, queryWrapper);

        BeanUtils.copyProperties(dishPageInfo, dishDtoPageInfo, "records");
//        however, the browser won't display the categoryName in that backend returns number but browser has to show string

        List<Dish> records = dishPageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {  // recodes is actually the list of all dishes, item is every dish
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);  // assign the other properties except categoryName

            Long categoryId = item.getCategoryId();  // get categoryId so that can get categoryName
            Category category = categoryService.getById(categoryId);
            if(category != null) {
                String categoryName = category.getName();  // get name
                dishDto.setCategoryName(categoryName);  // assign categoryName only
            }

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPageInfo.setRecords(list);

        return R.success(dishDtoPageInfo);
    }


    @GetMapping("/{id}")  // update dish but show first
    public R<DishDto> getDish(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return R.success("Dish updated!");
    }

    /**
     * get setmeal list in setmeal session
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus, 1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);
        return R.success(list);
    }
}
