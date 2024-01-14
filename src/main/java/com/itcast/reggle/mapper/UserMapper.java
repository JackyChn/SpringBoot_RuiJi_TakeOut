package com.itcast.reggle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itcast.reggle.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
