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
		height: 350px;
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
		<h1>회원가입</h1>
		<form action="MemberJoinPro.me" method="post" name="joinForm">
			<fieldset>
				<table>
					<tr>
						<td>이름</td>
						<td><input type="text" name="name" required="required"></td>
					</tr>
					<tr height="25px">
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>아이디</td>
						<td>
							<input type="text" name="id" required="required" 
								placeholder="4~12자리 영문,숫자 조합" onkeyup="checkId(this)">
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
								placeholder="8~16자리 영문,숫자,특수문자 조합">
						</td>
					</tr>
					<tr height="25px">
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>E-Mail</td>
						<td><input type="text" name="email" required="required"></td>
					</tr>
					<tr height="25px">
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="가입" />
							<input type="button" value="취소" onclick="history.back()" />
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</section>
</body>
</html>