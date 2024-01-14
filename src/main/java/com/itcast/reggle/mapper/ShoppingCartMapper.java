package com.itcast.reggle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itcast.reggle.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
