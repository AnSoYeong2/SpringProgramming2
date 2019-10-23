<%@ page contentType="text/html; charset=UTF-8"%>
<!-- c접두사를 이용해서 jstl(javascripttagLibrary사용하겠다) -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
</head>
<body>
	<h5>게시물 리스트</h5>
	<table class="table table-sm" >
		<thead>
			<tr style="background:orange">
				<th scope="col">번호</th>
				<th scope="col">제목</th>
				<th scope="col">글쓴이</th>
				<th scope="col">날짜</th>
				<th scope="col">조회수</th>
			</tr>
		</thead>
		<tbody>
		<!--  c: 태그라이브러리 (지시자에 태그라이브러리 추가 필요) -->
			<!-- 아래 코드는 for(Board board: boardList{}와 비슷함 var="board"는 board와 매칭, ${boardList}얘는 boardList와 매칭) -->
			<c:forEach var="board" items="${boardList}">
				<!-- boardList안에 있는 갯수만큼 반복하겠다 (여기서는 10개가 들어있으니까 10번 반복) -->
				<!-- fmt 접두사를 이용한 태그를 쓰기 위해서 taglib 써줘야 함 -->
				<tr>
					<th scope="row">${board.bno}</th>
					<td><a href="boardDetail?bno=${board.bno}">${board.btitle}</a></td>
					<td>${board.bwriter}</td>
					<td><fmt:formatDate value="${board.bdate}" pattern="yyyy-MM-dd" /></td>
					<td>${board.bhitcount}</td>
				</tr>
				
			</c:forEach>
		</tbody>
	</table>
	<div style="display: flex;" >
		<div style="flex-grow:1;">
			<a href="boardList?pageNo=1" class="btn btn-primary">처음</a>
			<!-- test의 값이 true면 if가 적용이 된다. false가 나오면 적용X -->
			<c:if test="${groupNo >1}">
				<a href="boardList?pageNo=${startPageNo-1}" class="btn btn-success">이전</a>
			</c:if>
				<div style="display: inline-block;" class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
				 <div class="btn-group mr-2" role="group" aria-label="First group">
				 	<c:forEach begin="${startPageNo}" end="${endPageNo}" var="i">
				 		<c:if test="${pageNo==i}">
				 			<a href="boardList?pageNo=${i}" class="btn btn-secondary active">${i}</a>
				 		</c:if>
				 		<c:if test="${pageNo!=i}">
				 			<a href="boardList?pageNo=${i}" class="btn btn-secondary">${i}</a>
				 		</c:if>
				 	</c:forEach>
				  </div>
				</div>	
				<c:if test="${groupNo <totalGroupNum}">
					<a href="boardList?pageNo=${endPageNo+1}" class="btn btn-success">다음</a>
				</c:if>
				<a href="boardList?pageNo=${totalPageNum}" class="btn btn-primary">맨끝</a>
		</div>
			<a href="writeBoardForm" class="btn btn-warning">글쓰기</a>
			<c:if test="${mid == null }">
				<a href="loginForm" class="btn btn-danger">로그인</a>
				<a href="join" class="btn btn-success">회원가입</a> 
			</c:if>
			<c:if test="${mid != null }">
				<a href="logout" class="btn btn-danger">로그아웃</a>
			</c:if>
	</div>
</body>
</html>