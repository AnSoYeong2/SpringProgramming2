<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="<%=application.getContextPath()%>/resources/js/jquery-3.4.1.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=application.getContextPath()%>/resources/bootstrap-4.3.1-dist/css/bootstrap.min.css" />
<script type="text/javascript"
	src="<%=application.getContextPath()%>/resources/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function btnLogin() {
		if ($("mid").val() == "")
			return false;
		if ($("mpassword").val() == "")
			return false;
		return true;
	}
	function btnLogout() {
		location.href="logout";
	}
	
	function jsonDownload1(){
		$.ajax({
			url:"jsonDownload1",
			success: function(data){
				$("#bno").html(data.bno);
				$("#btitle").html(data.btitle);
				$("#writer").html(data.writer);
				$("#date").html(data.date);
				$("#hitcount").html(data.hitcount);
				
				var html ="<tr>";
			      html += "		<td>"+	data.bno	+"</td>";
			      html += "		<td>"+	data.btitle	+"</td>";
			      html += "		<td>"+	data.writer	+"</td>";
			      html += "		<td>"+	data.date	+"</td>";
			      html += "		<td>"+	data.hitcount	+"</td>";
			    html +="</tr>";
			    $("tbody").append(html);
			}
		});
	}
	function jsonDownload2(){
		$.ajax({
			url:"jsonDownload2",
			success: function(data){
				$("#bno").html(data.bno);
				$("#btitle").html(data.btitle);
				$("#writer").html(data.writer);
				$("#date").html(data.date);
				$("#hitcount").html(data.hitcount);
		
				var html ="<tr>";
			      html += "		<td>"+	data.bno	+"</td>";
			      html += "		<td>"+	data.btitle	+"</td>";
			      html += "		<td>"+	data.writer	+"</td>";
			      html += "		<td>"+	data.date	+"</td>";
			      html += "		<td>"+	data.hitcount	+"</td>";
			    html +="</tr>";
			    $("tbody").append(html);
			}
		});
		
		
	}
</script>
</head>
<body>
	<h5>[HttpSession을 이용해서 로그인 구현]</h5>
	<div>
		<c:if test="${loginResult != 'success'}">
			<form id="loginForm" method="post" action="login">
				<div class="form-group">
					<label for="mid">아이디</label> 
					<input type="text" class="form-control" id="mid" name="mid">
					<c:if test="${loginResult=='wrongMid'}">
						<span style ="color:red;">로그인 아이디가 없습니다.</span>
					</c:if>
						
				</div>
				<div class="form-group">
					<label for="mpassword">패스워드</label>
					<input type="password" class="form-control" id="mpassword" name="mpassword">
					<c:if test="${loginResult=='wrongMpassword'}">
						<span style ="color:red;">패스워드가 틀립니다.</span>
					</c:if>
				</div>
				<!-- 로그인을 위해서 제대로 입력되었는지 확인해주기 위해 submit해주기 전에 처리해줄 수 있어 함수에서(true나올때만 submit동작되게 하는거지 -->
				<input onclick="return btnLogin()" type="submit"
					class="btn btn-primary" value="로그인"></input>
					
				<!-- submit효과가 없는 버튼(단지 버튼 모양만 있는거야) 함수를 호출할 뿐 -->
			</form>
		
		</c:if>
		<c:if test="${loginResult == 'success'}">
			<div id="logoutDiv">
				<!-- display:none은 안보이게 하겠다 완전히 사라지는 것은 아님! -->
				<button onclick="btnLogout()" class="btn btn-danger">로그아웃</button>
				<!-- 버튼을 누르면 함수가 호출된다(submit효과는 없음) -->
			</div>
		</c:if>
	</div>
	
	<h5>[OutputStream을 이용해서 파일 다운로드 구현]</h5>
	<div>
		<!-- 그냥 파일만 가져오면 될 경우  그냥  저경로에 항상 변하지않는 파일을 가져오고 싶을때 -->
		<img src="<%=application.getContextPath()%>/resources/image/Desert.jpg" width=100/>
		<br/>
		<!--  예를들어 사용자에 따라 다른 사진이 나와야 할 경우에  -->
		<img src="fileDownload?fname=Desert.jpg" width="100"/>
		<br/>
		<a href="fileDownload?fname=Desert.jpg">파일 다운로드</a>
		<br/>
		
		<h5>[Writer를 이용해서 JSON 데이터 다운로드]</h5>
		<a href ="javascript:jsonDownload1()">JSP에서 생성</a> <br/>
		<a href="javascript:jsonDownload2()">Controller에서 생성</a>
		<div>
			<table class="table table-sm">
			  <thead>
			    <tr>
			      <th scope="col">번호</th>
			      <th scope="col">제목</th>
			      <th scope="col">글쓴이</th>
			      <th scope="col">날짜</th>
			      <th scope="col">조회수</th>
			    </tr>
			  </thead>
			  <tbody>
			  </tbody>
			</table>
		</div>
		
	</div>
</body>
</html>