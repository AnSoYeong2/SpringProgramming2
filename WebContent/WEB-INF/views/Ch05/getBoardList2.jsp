<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*, com.mycompany.web.dto.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h5>게시물 리스트(전체 게시물 수: ${tatalNo})</h5>
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
  <!-- 아래 코드는 for(Board board: boardList{}와 비슷함 var="board"는 board와 매칭, ${boardList}얘는 boardList와 매칭) -->
  <c:forEach var="board" items="${boardList}" > <!-- boardList안에 있는 갯수만큼 반복하겠다 (여기서는 10개가 들어있으니까 10번 반복) -->
	<tr>
      <th scope="row">${board.bno}</th>
      <td>${board.btitle}</td>
      <td>${board.writer}</td>
      <td><fmt:formatDate value="${board.date}" pattern="yyyy-MM-dd"/></td>
      <td>${board.hitcount}</td>
    </tr>
    </c:forEach>
  </tbody>
</table>