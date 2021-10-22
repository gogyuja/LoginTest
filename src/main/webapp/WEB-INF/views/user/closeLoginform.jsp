<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
window.onload=function(){
	//부모의 웹페이지를 메인으로 이동
	window.opener.location.href="/";
	//자식 팝업을 닫는다.
	window.close();
}
</script>
</head>
<body>
<!-- 
	해당 jsp의 경우 바로 닫힌 후 부모의 로그인 페이지를 메인페이지로 이동시키기 위해 만들었다.
	원래는 ajax를 통해 소셜로그인정보를 받아온 후 거기서 닫은 뒤에 부모창을 메인으로 옮겨야되지만
	소셜로그인 웹페이지의 경우 해당회사가 만들어 둔거라 ajax를 어떻게 해야할지 몰라서 이런식으로 
	부모를 메인으로 옮긴 후 자식팝업을 닫는 jsp다.
 -->
</body>
</html>