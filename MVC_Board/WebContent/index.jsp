<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
// Ctrl+F11 사용 가능
</script>

<style>
a{
text-decoration: overline; /* 콘텐츠 위에 줄 */
text-decoration: line-through; /* 취소선 */
text-decoration: underline; /* 밑줄 */
text-decoration: none; 
}

header>a:link{
text-decoration: none;
color: black;
}
header>a:visited{
text-decoration: none;
color: black;
}
header>a:active{
text-decoration: none;
color: black;
}

header>a:hover{
color:red !important;
transition: 0.5s ;}

</style>

<body> 
<%
/* [1] session 객체로부터 id 속성값을 가져오기 */
String id = (String)session.getAttribute("id");
if(id == null){ //id == null도 가능 // .length getParameter때 사용%>
<header style="text-align: right;"><a href="MemberLoginForm.me" >로그인</a> | 
								<a href="MemberJoinForm.me" >회원가입</a></header>

<%}else{%>
<header style="text-align: right;"><%=session.getAttribute("id") %>님 | <a href="MemberLogout.me" >로그아웃</a> | 
								<a href="MemberModifyForm.me" >정보수정</a></header>	
<%}%>

	<h1>Main</h1>
<!-- 	<a href="Controller.bo">BoardFrontController Test</a> -->
	<h2><a href="BoardWriteForm.bo">글쓰기</a></h2>
	<h2><a href="BoardList.bo">글목록</a></h2>
	
</body>
</html>