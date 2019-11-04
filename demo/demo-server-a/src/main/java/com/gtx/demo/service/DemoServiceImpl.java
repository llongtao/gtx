package com.gtx.demo.service;

import com.gtx.demo.mapper.UserMapper;
import com.gtx.demo.model.User;
import com.gtx.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service( "demoService")
public class DemoServiceImpl implements DemoService {

    @Resource
    private UserMapper userMapper;


    @Override
    @Transactional
    @GlobalTransactional(name = "111",rollbackFor = Exception.class)
    public void addUser(User user) {
        userMapper.insertUser(user);
    }
}
