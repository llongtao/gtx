package com.llt.demo.service;

import com.llt.demo.model.User;
import com.llt.gtx.spring.annotation.GlobalTransactional;

public interface DemoService {

    @GlobalTransactional(name = "111",rollbackFor = Exception.class)
    void addUser(User user);
}
