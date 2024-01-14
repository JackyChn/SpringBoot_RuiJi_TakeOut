package com.itcast.reggle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itcast.reggle.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
