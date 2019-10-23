<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="<%=application.getContextPath()%>/resources/js/jquery-3.4.1.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=application.getContextPath()%>/resources/bootstrap-4.3.1-dist/css/bootstrap.min.css">
<script type="text/javascript"
	src="<%=application.getContextPath()%>/resources/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function checkForm() {
		var result = true;
		//모든 에러 내용 지우기
		$(".error").text("");
		$(".error").css("color", "red");
		//입력값 검사
		if ($("#mid").val() == "") {
			$("#midError").text("*아이디를 입력하세요.");
			result = false;
		}
		if ($("#mname").val() == "") {
			$("#mname").text("*이름을 입력하세요.");
			result = false;
		}
		if ($("#mpassword").val() == "") {
			$("#mpasswordError").text("*비밀번호를 입력하세요.");
			result = false;
		}
		return result;
	}
	
	function checkMid(){
		$.ajax({
			url: "checkMid",
			data: {"mid":$("#mid").val()},
			success: function(data) {
				if(data.result){
					$("#midError").text("*사용할 수 있는 아이디 입니다.");
					$("#midError").css("color", "green"); //한번 그린으로 바꾸면 계속 green이 되기 때문에 다시 red로 바꿔야 red로 나온다
				}else{
					$("#midError").text("*사용할 수 없는 아이디 입니다.");
					$("#midError").css("color", "red");
				}
			}
		});
	}
</script>
</head>
<body>
	<h5>회원가입</h5>
	<!-- action ="#"은 현재 페이지를 다시 보여준다를 의미 -->
<%-- 			<div class="form-group">
			 <input id="mid" name="mid" type="text"
				placeholder="아이디를 입력하세요."> <span id="midError" class="error"
				style="color: red">${midError }</span> <input type="button"
				value="중복체크" onclick="checkMid()" class="btn btn-danger" />
		</div> --%>
	
	<form method="post" action="join" onsubmit="return checkForm()">
		<div class="form-group">
			<label for="mid" >ID</label>
			<div class="input-group">
				<input id="mid" name ="mid" type="text" class="form-control"  placeholder="아이디를 입력하세요."/>
				<div class="input-group-appender">
					<input onclick="checkMid()" class="btn btn-danger" type="button" value="중복체크"/>
				</div>
			</div>
			<span id="midError" class="error" style="color: red"></span>
		</div>

		<div class="form-group">
			<label for="mname">NAME</label> 
			<input id="mname" name="mname" type="text" class="form-control" placeholder="이름을 입력하세요."> 
			<span id="mnameError" class="error" style="color: red"></span>
		</div>

		<div class="form-group">
			<label for="mpassword">PASSWORD</label> 
			<input id="mpassword" name="mpassword" type="text" class="form-control" placeholder="비밀번호를 입력하세요."/>
			<span id="mpasswordError"class="error" style="color: red"></span>
		</div>
		<div class="form-group">
			<input type="submit" class="btn btn-danger" value="회원가입" />
		</div>

	</form>
</body>
</html>