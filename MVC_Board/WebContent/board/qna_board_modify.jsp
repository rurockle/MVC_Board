<%@page import="vo.PageInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="vo.BoardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
//BoardBean 객체(article) 가져오기
BoardBean article = (BoardBean) request.getAttribute("article");
//page 파라미터 값 가져오기(page 식별자 지정 불가) => page 디렉티브 때문에 JSP의 예약어로 취급됨
String nowPage = (String) request.getAttribute("page");

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> 
	<title>MVC 게시판</title>
	<script type="text/javascript">
	function modifyboard(){
		modifyform.submit();
	}
	</script>
	<style type="text/css">
   #registForm{
      width: 500px;
      height: 600px;
      border : 1px solid red; 
      margin:auto; 
   }   
   h2{
      text-align: center;
   }
   table{
      margin:auto;
      width: 450px;
      }
   .td_left{
      width: 150px;
      background:orange;
   }
   .td_right{
      width: 300px;
      background:skyblue;
   }
   #commandCell{
      text-align: center;
      
   }
</style>
</head>
<body>
<!-- 게시판 등록 -->

<section id = "writeForm">
<h2>게시판글수정</h2>
<form action="BoardModifyPro.bo" method="post" name = "modifyform">
<input type = "hidden" name = "BOARD_NUM" value = "<%=article.getBOARD_NUM()%>"/>
<table>
	<tr>
		<td class="td_left">
			<label for = "BOARD_NAME">글쓴이</label>
		</td>
		<td class="td_right">
			<input type = "text" name="BOARD_NAME" id = "BOARD_NAME" value = "<%=article.getBOARD_NAME()%>"/>
		</td>
	</tr>
	<tr>
		<td class="td_left">
			<label for = "BOARD_PASS">비밀번호</label>
		</td>
		<td class="td_right">
			<input name="BOARD_PASS" type="password" id = "BOARD_PASS"/>
		</td>
	</tr>
	<tr>
		<td class="td_left">
			<label for = "BOARD_SUBJECT">제 목</label>
		</td>
		<td class="td_right">
			<input name="BOARD_SUBJECT" type="text" id = "BOARD_SUBJECT" value = "<%=article.getBOARD_SUBJECT()%>"/>
		</td>
	</tr>
	<tr>
		<td class="td_left">
			<label for = "BOARD_CONTENT">내 용</label>
		</td>
		<td>
			<textarea id = "BOARD_CONTENT" name="BOARD_CONTENT" cols="40" rows="15"><%=article.getBOARD_CONTENT()%></textarea>
		</td>
	</tr>
	<tr>
					<td class="td_left"><label for="board_file">파일첨부</label></td>
					<td class="td_right"><%=article.getBOARD_FILE() %></td>
	</tr>
</table>
	<section id = "commandCell">
			<input type="submit" value="수정">&nbsp;&nbsp;
			<input type="button" value="뒤로" onclick="history.back()">
	</section>
</form>
</section>
</body>
</html>