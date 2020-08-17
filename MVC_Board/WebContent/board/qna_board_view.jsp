<%@page import="vo.BoardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
BoardBean article = (BoardBean) request.getAttribute("article");
String nowPage = (String) request.getAttribute("page");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC게시판</title>
<style type="text/css">
	#articleForm {width: 500px; height: 500px; border: 1px solid red; margin: auto;}
	
	h2 {text-align: center;}
	
	#basicInfoArea {height: 40px; text-align: center;}
	
	#articleContentArea {
		background: orange;
		margin-top: 20px;
		height: 350px;
		text-align: center;
		overflow: auto; /* 지정 영역 크기 이상일 경우 자동으로 스크롤바 생성*/
	}
		
	#commandList {
		matgin: auto;
		width: 500;
		text-align: center;
	}
	header {
		text-align: right;
	}
</style>

</head>
<body>
<!--게시판 수정  -->
<section id="articleForm">

 <h2> 글 내용 상세보기</h2>
 <section id="basicInfoArea">
  제목:
  <%=article.getBOARD_SUBJECT() %>
  첨부파일 : 
			<%if(article.getBOARD_FILE() != null) { %>
				<!-- 파일이름 클릭 시 새창에서 다운로드 작업 수행 -->	
				<a href="BoardFileDown.bo?file_name=<%=article.getBOARD_FILE()%>" target="blank"><%=article.getBOARD_FILE() %></a>
			<%}%>
 </section>

 <section id="articleContentArea">
  <%=article.getBOARD_CONTENT() %>
 </section>
</section>
<section id = "commandList">
 <a href="BoardReplyForm.bo?board_num=<%=article.getBOARD_NUM()%>&page=<%=nowPage%>">[답변]</a>
 <a href="BoardModifyForm.bo?board_num=<%=article.getBOARD_NUM()%>&page=<%=nowPage%>">[수정]</a>
 <a href="BoardDeleteForm.bo?board_num=<%=article.getBOARD_NUM()%>&page=<%=nowPage%>">[삭제]</a>
 <a href="BoardList.bo?page=<%=nowPage%>">[목록]</a>
 &nbsp;&nbsp;
</section>

<!--게시판 수정  -->
</body>
</html>