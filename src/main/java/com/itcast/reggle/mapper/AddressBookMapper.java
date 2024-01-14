package com.itcast.reggle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itcast.reggle.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
