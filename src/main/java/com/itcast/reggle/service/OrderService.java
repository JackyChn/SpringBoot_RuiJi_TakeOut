package com.itcast.reggle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itcast.reggle.entity.Orders;

public interface OrderService extends IService<Orders> {

    void submit(Orders orders);
}
