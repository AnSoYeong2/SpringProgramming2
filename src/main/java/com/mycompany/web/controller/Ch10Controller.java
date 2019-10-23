package com.mycompany.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.web.dto.Ch10Board;
import com.mycompany.web.dto.Ch10Member;
import com.mycompany.web.service.Ch10Service;
import com.mycompany.web.service.LoginResult;


@Controller
@RequestMapping("/Ch10")
public class Ch10Controller {
	private static final Logger logger = LoggerFactory.getLogger("Ch10Controller");

	@Autowired
	private DataSource dataSource;

	@RequestMapping("/content") //http://localhost:8080/.../Ch10/content로 요청이 왔을 때 실행
	public String content() {
		return "Ch10/content";
	}

	@RequestMapping("/connTest")
	public void connTest(HttpServletResponse response) {
		boolean result = true;

		try {
			// 커넥션 풀에서 연결된 커넥션 대여
			Connection conn = dataSource.getConnection();
			if (conn != null)
				result = true;

			// 커넥션 풀로 커넥션을 반납
			conn.close();

		} catch (SQLException e) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// root-context에서 객체 만들어놈 <bean>태그로
	/*
	 * @Autowired private SqlSessionTemplate sqlSessionTemplate;
	 * 
	 * 
	 * @RequestMapping("/getMember") public String getMember(Model model,String mid)
	 * { //왜 selectOne이냐면 mid는 PK라서 한개뿐이야 한개만 가져오는거야 member안으로 들어가서 select의 아이디값을
	 * 적어주고 #{}으로 되어있는 변수에 값을 넣어줌 //그럼 무엇을 리턴해? resultMap 태그에 있는거를 리턴해주지(즉, Ch10의
	 * member) Ch10Member member =
	 * sqlSessionTemplate.selectOne("member.selectMemberByMid",mid);
	 * 
	 * model.addAttribute("member", member); //여기로 가라했어 그럼 getMember.jsp파일이 있어야지
	 * return "Ch10/getMember"; }
	 */
	
	@Autowired
	private Ch10Service service;
	
	@RequestMapping("/boardList")
	//만약에 pageNo가 넘어오지 않으면 default로 1이 넘어옴
	public String boardList(Model model, @RequestParam(defaultValue="1") int pageNo, HttpSession session) {
		
		//페이지 번호 세션에 저장
		session.setAttribute("PageNo",pageNo);
		
		//페이지당 행 수
		int rowsPerPage = 10;
		//이전, 다음을 클릭했을 때 나오는 페이지 수
		int pagesPerGroup = 5;
		//전체 게시물 수
		int totalRowNum = service.getTotalRowNum();
		//전체 페이지 수
		int totalPageNum=totalRowNum / rowsPerPage;
		if(totalRowNum % rowsPerPage !=0) totalPageNum++;
		//전체 그룹 수
		int totalGroupNum = totalPageNum/pagesPerGroup;
		if(totalPageNum % pagesPerGroup != 0) totalGroupNum++;
		//현재 페이지의 그룹번호
		int groupNo = (pageNo-1)/pagesPerGroup +1;
		//현재 그룹의 시작 페이지 번호
		int startPageNo = (groupNo-1)*pagesPerGroup+1;
		//현재 그룹의 마지막 페이지 번호
		int endPageNo =startPageNo + pagesPerGroup -1;
		if(groupNo == totalGroupNum) endPageNo = totalPageNum;
		//현재 페이지의 시작 행번호
		int startRowNo = (pageNo-1)*rowsPerPage +1; 
		//현재 페이지의 끝 행번호
		int endRowNo = pageNo * rowsPerPage;
		if(pageNo == totalPageNum) endRowNo = totalRowNum;
		
		//현재 페이지의 게시물 가져오기
		List<Ch10Board> boardList = service.getBoardList(startRowNo, endRowNo);
		//JSP로 페이지 정보 넘기기
		model.addAttribute("pagesPerGroup",pagesPerGroup);
		model.addAttribute("totalPageNum", totalPageNum);
		model.addAttribute("totalGroupNum", totalGroupNum);
		model.addAttribute("groupNo", groupNo);
		model.addAttribute("startPageNo", startPageNo);
		model.addAttribute("endPageNo", endPageNo);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("boardList", boardList);
		
		return "Ch10/boardList";
	}
	
	@RequestMapping("/writeBoardForm")
	public String writeBoardForm(HttpSession session) {
		String mid= (String)session.getAttribute("mid");
		if(mid == null) {
			return "redirect:/Ch10/loginForm";
		}
		
		return "Ch10/writeBoardForm";
	}
	
	@RequestMapping("/writeBoard") //클라이언트한테 이런 요청이 옴
	public String writeBoard(Ch10Board board, HttpSession session) {
		board.setBwriter((String)session.getAttribute("mid"));
		service.writeBoard(board);
		return "redirect:/Ch10/boardList"; 
		//요청이 들어왔는데 이 요청을 처리하고 나서 다시 같은 클래스 내의 다른 메소드를 호출하고자 할 때 그냥 boardList()라고 요청할 수 없음 
		//왜냐면 클라이언트가 /...으로 url을 요청함으로써 실행이 되는 메소드를 선언했으니까
		//사용자가 게시물에 글을 작성하고 나서 refresh했는데 주소는 writeBoard가 되어있는데 화면은 boardList임 그러면 다시 게시물 작성 페이지로 넘어가게 됨
		//사용자는 대체로 게시물을 작성하고 refresh를 통해 게시물이 작성이 되었는지 확인하고자 하는데 다시 게시물 작성 페이지로 넘어가면 문제있어
		//다시 나(서버)한테 boardList를 요청해야할 때 redirect를 사용 url이 바뀌어서 요청 
	}
	
	@RequestMapping("/loginForm")
	public String loginForm(String error, Model model) {
		if(error != null) {
			if(error.equals("fail_mid")) {
				model.addAttribute("midError", "*아이디가 존재하지 않습니다.");
			}else if(error.equals("fail_mpassword")) {
				model.addAttribute("mpasswordError", "*비밀번호가 일치하지 않습니다.");
			}
		}
		return "Ch10/loginForm";
	}
	
	@PostMapping("/login")
	public String login(String mid, String mpassword, HttpSession session) {
		//Service는 비즈니스에 대한 기능처리 메소드를 작성하고 처리하는 곳 
		//컨트롤러에서 이러한 기능처리를 하려고 할 필요 없고 Service로 가서 처리해주면 돼
		LoginResult result = service.login(mid,mpassword);
		
		if(result == LoginResult.FAIL_MID) {
			//model.addAttribute("midError","*아이디가 존재하지 않습니다.");
			//model은 request영역이라 응답이 오면 사라지는 범위야 따라서 이게 뜨지 않아 session영역에 써야해
			
			//post방식을 사용할 경우
			//session.setAttribute("midError","*아이디가 존재하지 않습니다.");
			//return "redirect:/Ch10/loginForm"; 
			return "redirect:/Ch10/loginForm?error=fail_mid"; //get방식의 경우 session쓰지 않고 이렇게도 쓸수있음
			
		}else if(result == LoginResult.FAIL_MPASSWORD) {
			return "redirect:/Ch10/loginForm?error=fail_mpassword";
		}
		session.setAttribute("mid", mid);
		return "redirect:/Ch10/boardList";
		
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("mid");
		return "redirect:/Ch10/boardList";
	}
	
	//회원가입 폼 제공
	@GetMapping("/join")
	public String joinForm() {
		return "Ch10/joinForm";
	}

	//회원가입 처리
	@PostMapping("/join")
	public String join(Ch10Member member) {
		service.join(member);
		return "redirect:/Ch10/loginForm";
	}
	
	@RequestMapping("/checkMid")
	public void checkMid(String mid, HttpServletResponse response) throws Exception {
		boolean result = service.checkMid(mid);
		response.setContentType("application/json; charset =UTF-8");
		PrintWriter pw = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		pw.print(jsonObject.toString());
		pw.flush();
		pw.close();
	}
	
	@RequestMapping("/boardDetail")
	public String boardDetail(int bno, Model model) {
		//조회수 
		service.increaseHitcount(bno);
		
		Ch10Board board = service.getBoard(bno);
		model.addAttribute("board", board);
		return "Ch10/boardDetail";
	} 
	
	@GetMapping("/boardupdate")
	public String updateBoardForm(int bno, Model model) {
		Ch10Board board = service.getBoard(bno);
		model.addAttribute("board", board);
		return "Ch10/updateBoardForm";
	}
	
	@PostMapping("/boardupdate")
	public String updateBoard(Ch10Board board, HttpSession session) {
		service.updateBoard(board);
		int pageNo =(Integer)(session.getAttribute("PageNo"));
		return "redirect:/Ch10/boardList?pageNo=" + pageNo;
	}
	
	@RequestMapping("/boarddelete")
	public String deleteBoard(int bno, HttpSession session) {
		service.updateBoard(bno);
		int pageNo =(Integer)( session.getAttribute("PageNo"));
		return "redirect:/Ch10/boardList?pageNo=" +pageNo;
	}
	
	
	
	
}
