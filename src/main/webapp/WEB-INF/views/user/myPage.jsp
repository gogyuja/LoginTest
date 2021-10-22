<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<jsp:include page="../top.jsp" />
	<button type="button" class="btn" onclick="location.href='/user/checkmodifyUser'">내 정보 수정으로 이동</button>
	<button type="button" class="btn" onclick="location.href='/user/deleteuserForm'">회원탈퇴 페이지로 이동</button>
</body>
</html>