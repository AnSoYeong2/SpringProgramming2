package com.mycompany.web.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ch09Dao3")
//@Component가 붙으면 Spring은 무조건 관리객체로 생각함(여기에 아이디 넣기)넣지 않으면 기본아이디로 만들어짐
public class Ch09Dao3 {

	private static final Logger logger = LoggerFactory.getLogger(Ch09Dao3.class);
	
	public void Ch09Dao3() {
		logger.debug("실행");
	}
	
	public void insert() {
		logger.debug("실행");
	}
	
}
