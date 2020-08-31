package com.gtx.demo.controller;

import com.alibaba.fastjson.JSON;
import com.gtx.demo.model.User;
import com.gtx.demo.service.DemoService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.client.ClientFactory;
import com.netflix.client.ClientRequest;
import com.netflix.client.IClient;
import com.netflix.client.IClientConfigAware;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.discovery.shared.Application;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.sun.deploy.config.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
public class DemoController {

    @Resource
    private DemoService demoService;

    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping("/a")
    String add(){
        User user = new User();
        user.setName("123");
        demoService.addUser(user);
        ILoadBalancer namedLoadBalancer = ClientFactory.getNamedLoadBalancer("gtx-tm-server");
        List<Server> reachableServers = namedLoadBalancer.getReachableServers();
        System.out.println(reachableServers.size());
        for (Server reachableServer : reachableServers) {
            System.out.println(reachableServer.getPort());
        }
        List<ServiceInstance> instances1 = discoveryClient.getInstances("gtx-tm-server");
        System.out.println(instances1);
        IClient namedClient = ClientFactory.getNamedClient("gtx-tm-server");
        ClientRequest clientRequest = new ClientRequest();
        IClientConfig iClientConfig = new DefaultClientConfigImpl("1");
        try {
            namedClient.execute(clientRequest,iClientConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(instances1);
    }
}
