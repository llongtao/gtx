package com.gtx.demoeurka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DemoEurkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoEurkaApplication.class, args);
    }

}
