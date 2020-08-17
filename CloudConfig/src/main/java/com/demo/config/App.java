package com.demo.config;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringCloudApplication
@EnableConfigServer
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class,args);
	}
}