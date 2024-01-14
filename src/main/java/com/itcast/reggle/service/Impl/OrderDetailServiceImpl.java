package com.itcast.reggle.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itcast.reggle.entity.OrderDetail;
import com.itcast.reggle.mapper.OrderDetailsMapper;
import com.itcast.reggle.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailsMapper, OrderDetail> implements OrderDetailService {
}
