package com.itcast.reggle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itcast.reggle.entity.Category;
import com.itcast.reggle.mapper.CategoryMapper;

public interface CategoryService extends IService<Category> {

    void removeCategoryById(Long id);
}
