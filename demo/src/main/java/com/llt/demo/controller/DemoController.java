package com.llt.demo.controller;

import com.llt.demo.model.User;
import com.llt.demo.service.DemoService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class DemoController {

    @Resource
    private DemoService demoService;

    @RequestMapping("/a")
    String add(){
        User user = new User();
        user.setName("123");
        demoService.addUser(user);
        return "";
    }
}
