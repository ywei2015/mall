package com.nokia.gateway;

import com.nokia.gateway.config.CaseInsenistiveFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringCloudApplication
@EnableZuulProxy
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public CaseInsenistiveFilter getCaseFilter() {
		return new CaseInsenistiveFilter();
	}
}