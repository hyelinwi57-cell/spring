<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/join.css">
</head>
<body>
	<jsp:include page="../layout/header.jsp"/>	
	
	<div class="page-header">
		<h1>회원가입</h1>
	</div>
	<div class="panel-body">
		<form method="POST" action="/join">
			<table>
				<tbody>
					<tr>
						<th>아이디</th>
						<td><input type="text" name="userId"></td>
					</tr>
					<tr>
						<th>패스워드</th>
						<td><input type="password" name="userPw">
						</td>
					</tr>
					<tr>
						<th>이름</th>
						<td><input type="text" name="userName">
						</td>
					</tr>
				</tbody>
			</table>
			<div class="panel-body-btns" style="padding-top:10px;">
				<button type="button" class="btn btn-sec" id="joinBtn">회원가입</button>
				<button type="reset" class="btn btn-fir" id="resetBtn">다시 작성</button>
				<button type="button" class="btn btn-fir" id="indexBtn">목록으로 이동</button>
			</div>	
		</form>
	</div>
	
	
	<jsp:include page="../layout/footer.jsp"/>
</body>
</html>