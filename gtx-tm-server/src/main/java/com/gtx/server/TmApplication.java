package com.gtx.server;

import com.gtx.server.netty.NettyServer;
import io.netty.util.concurrent.FastThreadLocalThread;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.annotation.Resource;

/**
 * @author LILONGTAO
 * @date 2019-11-05
 */
@EnableEurekaClient
@SpringBootApplication
public class TmApplication implements CommandLineRunner {

	@Resource
	private NettyServer nettyServer;

	public static void main(String[] args) {
		SpringApplication.run(TmApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new FastThreadLocalThread(()->nettyServer.run()).start();
	}
}
