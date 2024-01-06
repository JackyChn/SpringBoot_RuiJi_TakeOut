package com.itcast.reggle.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itcast.reggle.entity.Employee;
import com.itcast.reggle.mapper.EmployeeMapper;
import com.itcast.reggle.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
