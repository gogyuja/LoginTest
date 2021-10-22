<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
	<form action="/user/modify" name="joinForm" method="post">
		<div class="container" style="width:700px">
			<div>
				<label class="control-label" for="id">닉네임</label>
				<input class="form-control" type="text" name="nickname" id="nickname" value=${UserVO.nickname} required></>
				<span style="display:inline-block; alert-message; height:35px;" class="alert-message" id="nickname-alert"/>
			</div>
			<c:choose>
			<c:when test="${sessionScope.platform eq 'normal'}">
				 <!-- 이렇게 하면 이 스크립트가 일반회원이 정보수정할때만 적용된다. -->
				 <script src="<c:url value="/static/js/user/modifyForm.js"/>"></script> 
			<div>
				<label class="control-label" for="pw">비밀번호</label>
				<input class="form-control" type="password" name="pw" id="pw" required/>
				<span style="display:inline-block; alert-message; height:35px;" class="alert-message" id="pw-alert" />
			</div>
			<div>
				<label class="control-label" for="pw2">비밀번호 확인</label>
				<input class="form-control" type="password" name="pw2"	id="pw2" required/>
				<span style="display:inline-block; alert-message; height:35px;" class="alert-message" id="pw2-alert" />
			</div>
			</c:when>
			<c:otherwise>
				 <script src="<c:url value="/static/js/user/kakaomodifyForm.js"/>"></script>
			</c:otherwise>
			</c:choose>
			<button class="btn btn-primary" type="submit">회원정보수정</button>
		</div>
	</form>
</body>
</html>