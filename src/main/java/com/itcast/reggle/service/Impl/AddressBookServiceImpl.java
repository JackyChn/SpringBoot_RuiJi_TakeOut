package com.itcast.reggle.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itcast.reggle.entity.AddressBook;
import com.itcast.reggle.mapper.AddressBookMapper;
import com.itcast.reggle.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
