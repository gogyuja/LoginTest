<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<c:url value="/static/js/user/joinKind.js"/>"></script>
<link rel="stylesheet" href="/static/css/user/join.css">
</head>
<body>
	<jsp:include page="../top.jsp" />
		<center>
			<h1>회원가입종류</h1>
			<br/>
			<form method="get" name="joinForm">
			<a href="#" onclick="normalJoin();">
				<img width="50" height="50" src="/static/images/join/people.png">일반회원가입
			</a>
			<br/><br/>
			<a href="#" onclick="kakaoJoin();">
				<img src="/static/images/kakao/kakao_login_medium_narrow.png">
			</a>
			</form>
		</center>
</body>
</html>