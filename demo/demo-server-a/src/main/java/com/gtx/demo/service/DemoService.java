package com.gtx.demo.service;

import com.gtx.demo.model.User;
import com.gtx.spring.annotation.GlobalTransactional;

public interface DemoService {

    @GlobalTransactional(name = "111",rollbackFor = Exception.class)
    void addUser(User user);
}
