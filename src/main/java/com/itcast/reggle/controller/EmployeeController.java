package com.itcast.reggle.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itcast.reggle.common.R;
import com.itcast.reggle.entity.Employee;
import com.itcast.reggle.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
//        1. encrypt pwd by MD5
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

//        2. select by username in db
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

//        3. compare pwd, if disaccord then return login failed
        if (emp == null || !emp.getPassword().equals(password)) {
            return R.error("Login failed");
        }

//        4. check employee status, 0 then return result
        if (emp.getStatus() == 0) {
            return R.error("Account has been forbidden, plz contact the admin");
        }

//        5. login success, store employee id in session
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
//        1. clear id in session
        request.getSession().removeAttribute("employee");
        return R.success("Logout success!");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("Adding new employee: {}", employee);

//        set employee property
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);  // session id
        employee.setUpdateUser(empId);
        employeeService.save(employee);

        return R.success("New employee added!");
    }

    @GetMapping("/page")
    public R<Page> pageQuery(int page, int pageSize, String name) {
        log.info("page: {}, pageSize: {}, name: {}", page, pageSize, name);
//        page constructor
        Page pageInfo = new Page<>();

//        condition constructor
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(name), Employee::getName, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);

//        query
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> updateStatus(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("set employee status: {}", employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return R.success("Status updated!");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("query employee info by id: {}", id);
        Employee emp = employeeService.getById(id);
        if(emp != null) return R.success(emp);
        return R.error("No such employee");
    }
}
