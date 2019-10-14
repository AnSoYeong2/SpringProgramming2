package com.mycompany.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.web.dao.Ch09Dao;
import com.mycompany.web.dao.Ch09Dao2;

@Component("ch09Service")
public class Ch09Service {
	private static final Logger logger = LoggerFactory.getLogger(Ch09Service.class);
	
	//어떻게 객체를 대입할까? Spring이 관리하는 객체로 사용하기 위한 방법: 1.생성자주입 2.setter주입
	@Autowired
	private Ch09Dao ch09Dao;
	//필드(초기화 안한), Ch09Service는 Ch09Dao를 사용한다.(=Ch09Dao 없이는 Ch09Service를 사용할 수 없다.)
	//setter주입 방법, 외부에서 값을 받아서 값을 초기화하겠다.
	//setter 먼저  객체가 생성되야 method1이 값을 받아서 실행이 되겠지
	//property는 setter주입이기 때문에 setter가 있어야 함 
	
	//생성자, 작성안하는게 좋음
	//호출이 됐다 안됐다를 통해 객체가 생성 되고 안되고를 판단할 수 있음
	
	public void method1() {
		logger.debug("실행");
		ch09Dao.insert();
	}
	
	
	
}
