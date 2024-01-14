package com.itcast.reggle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itcast.reggle.common.BaseContext;
import com.itcast.reggle.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailsMapper extends BaseMapper<OrderDetail> {
}
