<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
	#joinFormArea {
		margin: auto;
		width: 400px;
		height: 250px;
		border: 2px double purple;
		border-radius: 10px;
		text-align: center;
	}
	
	fieldset {
		text-align: center;
		border: none;
	}
	
	#selectButton {
		margin-top: 10px;
	}
	
	table {
		width: 300px;
		margin: auto;
	}
	
</style>


</head>
<body>
<section id="joinFormArea">
		<h1>로그인</h1>
		<form action="MemberLoginPro.me" method="post" name="joinForm">
			<fieldset>
				<table>
					
					<tr>
						<td>아이디</td>
						<td>
							<input type="text" name="id" required="required" 
								placeholder="아이디를 입력하세요">
						</td>
					</tr>
					<tr height="25px">
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>패스워드</td>
						<td>
							<input type="text" name="passwd" required="required" 
								placeholder="비밀번호를 입력하세요">
						</td>
					</tr>
					<tr height="25px">
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="로그인" />
							<input type="button" value="가입" onclick="MemberJoinForm.me" />
							<input type="button" value="취소" onclick="history.back()" />
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</section>

</body>
</html>