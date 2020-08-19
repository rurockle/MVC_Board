<%@page import="member.vo.MemberBean"%>
<%@page import="board.vo.PageInfo"%>
<%@page import="board.vo.BoardBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// request 객체로부터 전달받은 ArrayList<MemberBean> 객체 가져오기
	ArrayList<MemberBean> memberList = 
						(ArrayList<MemberBean>)request.getAttribute("memberList");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<style type="text/css">
	h2 {
		text-align: center;
	}
	
	table {
		margin: auto;
		width: 800px;
		border: 1px solid darkgray;
	}
	
	a {
		text-decoration: none;
	}

	#tr_top {
		background: orange;
		width: 800px; 
		text-align: center;
	}
	
	td {
		text-align: center;
	}
	
	#button {
		margin: auto;
		width: 800px;
		text-align: right;
	}
	
	#pageList {
		margin: auto;
		width: 800px;
		text-align: center;
	}
	
	#emptyArea {
		margin: auto;
		width: 800px;
		text-align: center;
	}
</style>
</head>
<body>
	<!-- 회원 목록 -->
	<section id="listForm">
		<h2>회원 목록</h2>
		<table>
			<tr id="tr_top">
				<td width="100">
					<a href="MemberList.me?orderTarget=idx&orderType=asc">▲</a>
					번호
					<a href="MemberList.me?orderTarget=idx&orderType=desc">▼</a>
				</td>
				<td width="150">
					<a href="MemberList.me?orderTarget=name&orderType=asc">▲</a>
					이름
					<a href="MemberList.me?orderTarget=name&orderType=desc">▼</a>
				</td>
				<td width="150">아이디</td>
				<td width="150">패스워드</td>
				<td width="250">이메일</td>
				<td width="150">가입일</td>
			</tr>
			<!-- ArrayList<MemberBean> 객체가 null 이 아닐 때 반복문으로 회원 목록 출력 -->
		<%
		if(memberList != null) {
			for(MemberBean member : memberList) {
		%>
			<tr>
				<td>
					<input type="button" name="delete" value="삭제" 
						onclick="location.href='MemberDelete.me?idx=<%=member.getIdx()%>'">
					<%=member.getIdx() %>
				</td>
				<td><%=member.getName() %></td>
				<td><%=member.getId() %></td>
				<td><%=member.getPasswd() %></td>
				<td><%=member.getEmail() %></td>
				<td><%=member.getRegDate() %></td>
			</tr>		
		<%	
		}
		%>
			
		<%} else { %>
			<tr><td colspan="6">회원 목록이 없습니다.</td></tr>
		<%} %>	
		</table>		
	</section>
</body>
</html>


















