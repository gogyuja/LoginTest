<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<c:url value="/static/js/user/login.js"/>"></script>
<link rel="stylesheet" href="/static/css/user/loginForm.css">
</head>
<body>
	<jsp:include page="../top.jsp" />

<form action="/user/login" name="loginForm" method="POST">
  <div class="container" style="width:700px">
    <label for="id">아이디</label>
    <input type="text" placeholder="아이디를 입력하세요" id="id" name="id" required>

    <label for="pw"><b>비밀번호</b></label>
    <input type="password" placeholder="비밀번호를 입력하세요" name="pw" required>
        
    <button type="submit">로그인</button>
  
  	<a href="#" onclick="kakaoLogin();">
		<img style="width:130px; height:48px" src="/static/images/kakao/kakao_login_large.png">
	</a>
  </div>
</form>	

</body>
</html>