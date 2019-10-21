package com.mycompany.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.web2.service.Ch09CommonService;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private Ch09CommonService ch09CommonService;
	
	public HomeController() {
		logger.debug("실행");
	}
	
	@RequestMapping("/")
	public String home() {
		logger.debug("dispatchehome실행");
		ch09CommonService.method1();
		return "home";
	}
	
	
	@RequestMapping("/info")
	public String info() {
		logger.debug("실행");
		return "info";
	}
	

}
