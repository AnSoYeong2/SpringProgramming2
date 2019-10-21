<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">

body, html{
   height:100%;
   
}

#wrapper {   
   width:100%;
   height:100%;
   display:flex;
   flex-direction:column;
   /*position:fixed;
   top:0px;
   right:0px;
   bottom:0px;
   left:0px;*/
}

#header {
   border-bottom: 1px solid black;
   margin-bottom: 10px;
}

#content {
   flex-glow:1;
   height:80%;
   display: flex;
   min-height:0;
}

#sideBar {
   width: 400px;
   background-color: lightgray;
   padding-right: 10px;
   border-right: 1px solid black;
   overflow-y: scroll;
}

#center {
   flex-grow: 1;
   padding: 10px;
}

#center iframe {
   margin-top: 0px;
   width: 100%;
   height: 100%;
}

#footer {
   border-top: 1px solid black;
   margin-top: 10px;
   margin-bottom:10px;
}
</style>
</head>
<body>
   <div id="wrapper">
      <div id="header">
         <h3>SpringProgramming2</h3>
      </div>
      <div id="content">
         <div id="sideBar">
            <ul>
            	<li><a href="info" target="iframe">컨트롤러 생성</a></li>    
            	<li><a href="Chap02/content" target="iframe">요청 매핑</a></li>
            	<li><a href="Ch03/content" target="iframe">요청 파라미터</a></li>    
            	<li><a href="Ch04/content" target="iframe">요청 헤더 값과 쿠키값 설정 및 읽기</a></li>      
            	<li><a href="Ch05/content" target="iframe">컨트롤러에서 뷰로 데이터 전달</a></li>  
            	<li><a href="Ch06/content" target="iframe">매개변수 타입과 리턴 타입(&파일 업로드)</a></li>  
            	<li><a href="Ch08/content" target="iframe">파일 업로드</a></li>    
            	<li><a href="Ch09/content" target="iframe">의존성 주입(DI)</a></li>
            	<li><a href="Ch10/content" target="iframe">데이터베이스 연동</a></li>  
            </ul>
         </div>
         <div id="center">
            <iframe name="iframe" src="http://tomcat.apache.org" frameborder="0"></iframe>
         </div>
      </div>
      <div id="footer">2019.IoT.ASY</div>
   </div>
</body>
</html>