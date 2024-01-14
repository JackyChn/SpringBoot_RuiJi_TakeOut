package com.itcast.reggle.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itcast.reggle.entity.User;
import com.itcast.reggle.mapper.UserMapper;
import com.itcast.reggle.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
