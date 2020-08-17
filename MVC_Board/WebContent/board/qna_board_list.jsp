<%@ page import="vo.PageInfo"%>
<%@ page import="vo.BoardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%
	/* [1] Action 클래스에서 request 객체의 setAttribute() 메서드로 저장되어 전달된 객체 가져오기*/
	ArrayList<BoardBean> articleList=(ArrayList<BoardBean>)request.getAttribute("articleList");
    PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
	
    /* [2] PageInfo 객체로부터 페이징 정보 가져오기 */
    int listCount=pageInfo.getListCount();
	int nowPage=pageInfo.getPage(); // 내장 객체인 page 이름과 중복되므로 nowPage라는 변수명 지정 
	int maxPage=pageInfo.getMaxPage();
	int startPage=pageInfo.getStartPage();
	int endPage=pageInfo.getEndPage();
	
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>MVC 게시판</title>
<style type="text/css">
#registForm {
	width: 500px;
	height: 600px;
	border: 1px solid red;
	margin: auto;
}

h2 {
	text-align: center;
}

table {
	margin: auto;
	width: 500px;
}

#tr_top {
	background: orange;
	text-align: center;
}

#pageList {
	margin: auto;
	width: 550px;
	text-align: center;
}

#emptyArea {
	margin: auto;
	width: 500px;
	text-align: center;
}
</style>
</head>

<body>
	<!-- 게시판 리스트 -->


	<section id="listForm">
		<h2>
			글 목록<a href="BoardWriteForm.bo">게시판글쓰기</a>
		</h2>
		<table>
			<%
if(articleList != null && listCount > 0){
%>

			<tr id="tr_top">
				<td>번호</td>
				<td>제목</td>
				<td>작성자</td>
				<td>날짜</td>
				<td>조회수</td>
				<td>아이피</td>
			</tr>

			<%
		for(int i=0;i<articleList.size();i++){
			
	%>
			<tr>
				<td><%=articleList.get(i).getBOARD_NUM()%></td>

				<td>
					<%if(articleList.get(i).getBOARD_RE_LEV()!=0){ %> <%for(int a=0;a<=articleList.get(i).getBOARD_RE_LEV()*2;a++){ %>
					&nbsp; <%} %> ▶ <%}else{ %> ▶ <%} %> <a
					href="BoardDetail.bo?board_num=<%=articleList.get(i).getBOARD_NUM()%>&page=<%=nowPage%>">
						<%=articleList.get(i).getBOARD_SUBJECT()%>
				</a>
				</td>
<%
SimpleDateFormat s = new SimpleDateFormat("yy-MM-dd");
Date date = articleList.get(i).getBOARD_DATE();
%>

				<td><%=articleList.get(i).getBOARD_NAME() %></td>
				<td><%=s.format(date) %></td>
				<td><%=articleList.get(i).getBOARD_READCOUNT() %></td>
				<td><%=articleList.get(i).getBOARD_IP() %></td>
			</tr>
			<%} %>
		</table>
	</section>

	<section id="pageList">
		<%if(nowPage<=1){ %>
		[이전]&nbsp;
		<%}else{ %>
		<a href="BoardList.bo?page=<%=nowPage-1 %>">[이전]</a>&nbsp;
		<%} %>

		<%for(int a=startPage;a<=endPage;a++){
				if(a==nowPage){%>
		[<%=a %>]
		<%}else{ %>
		<a href="BoardList.bo?page=<%=a %>">[<%=a %>]
		</a>&nbsp;
		<%} %>
		<%} %>

		<%if(nowPage>=maxPage){ %>
		[다음]
		<%}else{ %>
		<a href="BoardList.bo?page=<%=nowPage+1 %>">[다음]</a>
		<%} %>
	</section>
	<%
    }
	else
	{
	%>
	<section id="emptyArea">등록된 글이 없습니다.</section>
	<%
	}
%>

</body>
</html>