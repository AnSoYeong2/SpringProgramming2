package com.mycompany.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.web.service.Ch09Service;
import com.mycompany.web.service.Ch09Service2;
import com.mycompany.web.service.Ch09Service3;

@Controller//얘가 component역할을 해서 얘는 따로 안붙여도 됩니다욧
@RequestMapping("/Ch09")
public class Ch09Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch09Controller.class);
	
	public Ch09Controller() {
		logger.debug("실행");
	}
	
	@Autowired 
	private Ch09Service ch09Service;
	@Autowired
	private Ch09Service2 ch09Service2;
	@Autowired
	private Ch09Service3 ch09Service3;
	
	@RequestMapping("/content")
	public String content() {
		return "Ch09/content";
	}
	
	//property는 setter주입이기 때문에 setter가 있어야 함 

	//Spring이 호출

	@RequestMapping("/method1")
	public String method1() {
		logger.debug("실행");
		ch09Service.method1();
		return "redirect:/Ch09/content";
	}

	@RequestMapping("/method2")	
	public String method2() {
		logger.debug("실행");
		ch09Service2.method2();
		return "redirect:/Ch09/content";
	}

	@RequestMapping("/method3")
	public String method3() {
		logger.debug("실행");
		ch09Service3.method3();
		return "redirect:/Ch09/content";
	}
	
	
}
