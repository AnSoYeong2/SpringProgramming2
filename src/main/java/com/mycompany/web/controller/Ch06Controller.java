package com.mycompany.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.web.dto.Ch06Board;

@Controller
@RequestMapping("/Ch06")
public class Ch06Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch06Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		return "Ch06/content";
	}
	
	@PostMapping("/login")
	public String login(String mid, String mpassword, HttpSession session) {
		String loginResult = "";
		if(mid.equals("admin")) {
			if(mpassword.equals("iot12345")) {
				loginResult="success";
			}else {
				loginResult="wrongMpassword";
			}
		}else {
			loginResult ="wrongMid";
		}
		session.setAttribute("loginResult", loginResult);
		
		return "redirect:/Ch06/content"; //요청을 다시하라는 것을 의미 어떤 요청이냐면 위에서 이루어진 "/content" 매핑 요청 즉 새로운 url검색이 아니라 기존 url로 돌아가달라는 거지
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginResult");
		return "redirect:/Ch06/content";
	}
	
	//fileDownload는 단지 파일을 불러오기만 하면 돼 (응답을 jsp에서 받지 않아도 돼) 그러면 리턴타입: void!
	@RequestMapping("/fileDownload")
	public void fileDownload(String fname, HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug(fname);
		//응답 헤더에 추가
		//response.setHeader("Content-Type","text/html; charset=UTF-8");//이렇게 contentType이 이렇다라고 둘다 명시해줘도 되고,
		//response.setHeader("Content-Type","application/json; charset=UTF-8")
		ServletContext application = request.getServletContext();
		String mimeType = application.getMimeType(fname);
		response.setHeader("Content-Type", mimeType);
		
		String userAgent = request.getHeader("User-Agent");
		String downloadName;
		if(userAgent.contains("Trident/7.0") || userAgent.contains("MSIE")) {
			//IE11 버전 또는 IE10이하 버전에서 한글 파일을 복원하기 위해
			downloadName = URLEncoder.encode("풍경.jpg", "UTF-8");
		}else {
			//webkit 기반 브라우저(Chrome, Safari, FireFox, Opera, Edge)에서 한글 파일을 복원하기 위해 사용
			downloadName = new String("풍경.jpg".getBytes("UTF-8"), "ISO-8859-1");
			
		}
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadName + "\"");
		
		//response.setContentType("image/png");//contentType은 항상 필요한 것이기 때문에 요렇게 해서 값만 딱 넣어줘도 돼
		
		//응답 바디에 데이처 추가
		/*  PrintWriter pw = response.getWriter(); pw.print("<!DOCTYPE html>");
		 * pw.print("<html><body>Hello</body></html>"); pw.flush(); pw.close();
		 *pw.print("{\"result\":\"ok\"}");
		 */
		//application에 저장된 경로가 실제경로임
		
		String realPath = application.getRealPath("/resources/image/" + fname);//war파일 내부의 경로를 상대경로로 해서 실제경로를 상황에따라 찾아주는거지
		File file = new File(realPath);
		response.setHeader("Content-Length", String.valueOf(file.length()));
		
		OutputStream os = response.getOutputStream();
		InputStream is = new FileInputStream(realPath);//절대경로는 어디에 클론하냐에따라 달라질수있음//servlet한테 물어보고 찾아내는게 현명해
	
		byte[] buffer = new byte[1024];
		while(true) {
			int readByte = is.read(buffer);
			if(readByte == -1) break;
			os.write(buffer,0, readByte);
		}
		os.flush();
		os.close();
		is.close();
	} 
	
	@RequestMapping("/jsonDownload1")
	public String jsonDownload1(Model model) {
		Ch06Board board = new Ch06Board();
		board.setBno(100);
		board.setBtitle("공부하고 싶다");
		board.setBcontent("까짓거 하면 되겠지 열공!");
		board.setWriter("소영이");
		board.setDate(new Date());
		board.setHitcount(1);
		model.addAttribute("board", board);
		
		return "Ch06/jsonDownload1";
	}
	
	@RequestMapping("/jsonDownload2")
	public void jsonDownload2(HttpServletResponse response) throws Exception{
		Ch06Board board = new Ch06Board();
		board.setBno(300);
		board.setBtitle("나는 리얼 오타쟁이");
		board.setBcontent("까짓거 고치면 되겠지 열공!");
		board.setWriter("소영이");
		board.setDate(new Date());
		board.setHitcount(1);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("bno", board.getBno());
		jsonObject.put("btitle", board.getBtitle());
		jsonObject.put("bcontent", board.getBcontent());
		jsonObject.put("writer", board.getWriter());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		jsonObject.put("date", sdf.format(board.getDate()));
		jsonObject.put("hitcount", board.getHitcount());
		
		String json = jsonObject.toString();
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.flush();
		pw.close();
	}
}
