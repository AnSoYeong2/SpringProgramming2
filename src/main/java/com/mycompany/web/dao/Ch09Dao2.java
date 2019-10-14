package com.mycompany.web.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ch09Dao2")
public class Ch09Dao2 {
	private static final Logger logger = LoggerFactory.getLogger(Ch09Dao2.class);
	
	public void Ch09Dao2() {
		logger.debug("실행");
	}
	
	public void insert() {
		logger.debug("실행");
		
	}
}
