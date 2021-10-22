<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="/">메인화면</a>
			</div>
		<ul class="nav navbar-nav navbar-right" >
			<c:choose>
				<c:when test="${sessionScope.user==null}">
					<li><a href="/user/loginForm">로그인</a></li>
					<li><a href="/user/join/termForm">회원가입</a></li>
				</c:when>
				<c:when test="${sessionScope.user!=null }">
					<li><a href="/user/myPage">마이페이지</a></li>
					<li><a href="/user/logout">로그아웃</a></li>
				</c:when>
			</c:choose>
		</ul>
		</div>
	</nav>	
</body>
</html>