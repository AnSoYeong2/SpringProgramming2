package com.mycompany.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.web.dto.Ch10Member;

@Controller
@RequestMapping("/Ch10")
public class Ch10Controller {
	private static final Logger logger = LoggerFactory.getLogger("Ch10Controller");
	
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping("/content")
	public String content() {
		return "Ch10/content";
	}
	@RequestMapping("/connTest")
	public void connTest(HttpServletResponse response){
		boolean result = true;
		
		try {
			//커넥션 풀에서 연결된 커넥션 대여
			Connection conn = dataSource.getConnection();
			if(conn != null) result = true;
			
			//커넥션 풀로 커넥션을 반납
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		

		try {
		response.setContentType("application/json; charset=UTF-8"); 
		PrintWriter pw = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		pw.print(jsonObject.toString());
		pw.flush();
		pw.close();
		}
		catch(IOException e) {e.printStackTrace();}	
	}
	//root-context에서 객체 만들어놈 <bean>태그로
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@RequestMapping("/getMember")
	public String getMember(Model model,String mid) {
		//왜 selectOne이냐면 mid는 PK라서 한개뿐이야 한개만 가져오는거야 member안으로 들어가서 select의 아이디값을 적어주고 #{}으로 되어있는 변수에 값을 넣어줌
		//그럼 무엇을 리턴해? resultMap 태그에 있는거를 리턴해주지(즉, Ch10의 member)
		Ch10Member member = sqlSessionTemplate.selectOne("member.selectMemberByMid",mid);
		
		model.addAttribute("member", member);
		 //여기로 가라했어 그럼 getMember.jsp파일이 있어야지
		return "Ch10/getMember";
	}
	
	@RequestMapping("/insertMember")
	public String insertMember() {
		System.out.println("dddd");
		Ch10Member ch10Member = new Ch10Member();
		
		ch10Member.setMid("1");
		ch10Member.setMname("soyeong");
		ch10Member.setMpassword("123456");
		
		sqlSessionTemplate.insert("member.insertMember",ch10Member);
		return "Ch10/getMember";
	}
	
}
