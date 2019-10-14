package com.mycompany.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Ch04")
public class Ch04Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch04Controller.class);
	
	@RequestMapping("/content")
	public String content(@RequestHeader("User-Agent") String userAgent, HttpServletRequest request) {
		logger.info("userAgent: " + userAgent);
		String browserKind = null;
		if(userAgent.contains("Chrome")) {
			browserKind = "Chrome";
		}else if(userAgent.contains("Trident/7.0"))
		{
			browserKind ="IE 11";
		}
		
		request.setAttribute("browserKind", browserKind );
		return "Ch04/content";
	}
	@RequestMapping("/setCookie")
	public String setCookie(HttpServletResponse response, String mname) {
		Cookie cookie = new Cookie("mname",mname);
		//cookie.setMaxAge(365*24*60*60);//365일동안 쿠키 저장 -> 이런 경우는 파일시스템 어딘가에 저장
		//cookie.setMaxAge(0); //기간 끝나기 전에 쿠키값 삭제하려면
		response.addCookie(cookie);
		return "Ch04/content";
	}
	@RequestMapping("/getCookie")
	public String getCookie(HttpServletRequest request) {
		Cookie[] cookies= request.getCookies();
		if(cookies != null) {
			for(Cookie cookie: cookies) {
				if(cookie.getName().equals("mname")) {
					request.setAttribute("mname", cookie.getValue());
				}
			}
		}
		return "Ch04/getCookie";
	}
}
