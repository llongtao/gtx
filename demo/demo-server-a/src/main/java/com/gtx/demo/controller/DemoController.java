package com.gtx.demo.controller;

import com.gtx.demo.model.User;
import com.gtx.demo.service.DemoService;
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
