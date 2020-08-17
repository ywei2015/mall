package com.demo.eureka;


import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringCloudApplication
@EnableEurekaServer
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}