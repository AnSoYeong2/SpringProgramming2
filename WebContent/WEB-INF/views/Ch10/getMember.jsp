<%@ page contentType="text/html; charset=UTF-8"%>
<!-- import도 필요없어 -->

<h5>멤버 정보</h5>
<table class="table table-sm">
  <thead>
    <tr>
      <th scope="col">아이디</th>
      <th scope="col">이름</th>
      <th scope="col">패스워드</th>
    </tr>
  </thead>
  <tbody>
  	<tr>
  	<!-- 필드값은 private이잖아 그래서 getter setter써가지고 값넣고 값가져오고 하는 거야(DTO에서 getter setter가 꼭필요한 이유야) 여기는 getter인거지 -->
      <td>${member.mid}</td>
      <td>${member.mname }</td>
      <td>${member.mpassword}</td>
    </tr>

  </tbody>
</table>