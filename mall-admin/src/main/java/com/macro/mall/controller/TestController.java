package com.macro.mall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

	@GetMapping("/hello")
	public String hello(HttpServletRequest request){
		String ip = request.getRequestURI();
		return "hello _ jenkins" + ip;
	}
}
