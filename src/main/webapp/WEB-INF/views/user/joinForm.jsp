<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="<c:url value="/static/js/user/join.js"/>"></script>
<link rel="stylesheet" href="/static/css/user/join.css">
</head>
<body>
	<jsp:include page="../top.jsp" />
	<div class="container" style="width:700px">
		<h1>회원가입</h1>
		<form action="/user/join" name="joinForm" method="POST">
				<div class="form-group has-feedback">
					<label class="control-label" for="id">아이디</label>
					<input class="form-control" type="text" name="id" id="id" required="required">
					<span style="display:inline-block; alert-message; height:15px;" class="alert-message" id="id-alert"/>
					<!-- 인라인요소인 span에 높이주기 -->
					<!-- https://lookingfor.tistory.com/entry/CSS-span-%ED%83%9C%EA%B7%B8-width-height-%EC%A7%80%EC%A0%95%ED%95%98%EA%B8%B0 -->
				</div>
				<div class="form-group has-feedback">
					<label class="control-label" for="pw">비밀번호</label>
					<input class="form-control" type="password" name="pw" id="pw"/>
					<span style="display:inline-block; alert-message; height:15px;" class="alert-message" id="pw-alert"/>
				</div>
				<div class="form-group has-feedback">
					<label for="pw2" class="control-label">비밀번호 확인</label>
					<input class="form-control" type="password" name="pw2" id="pw2"></>
					<span style="display:inline-block; alert-message; height:15px;" class="alert-message" id="pw2-alert" />
				</div>
				<div class="form-group has-feedback">
					<label for="nickname" class="control-label">닉네임</label>
					<input class="form-control" type="text" name="nickname" id="nickname"></>
					<span style="display:inline-block; alert-message; height:15px;" class="alert-message" id="nickname-alert"/>
				</div>
				<div class="form-group has-feedback">
					<label class="control-label">성별</label>
					<div>
						<input type="radio" name="gender" id="id-gender-male"value="1"/>
						<label for="id-gender-male">남자</label>
					</div>
					<div>
						<input type="radio" name="gender" id="id-gender-female" value="0"/>
						<label for="id-gender-female">여자</label>
					</div>
				</div>
				<div class="form-group has-feedback; mail-wrap">
					
					<div class="form-group has-feedback; mail-input-box">
						<label class="control-label" for="email">이메일</label>
						<input class="form-control" type="text" name="email" id="email" required="required">
						<span style="display:inline-block; alert-message; height:15px;" class="alert-message" id="email-alert"/>
					</div>	
								

					<div class="form-group has-feedback; mail-check-input-box" id="mail-check-input-box-false">
						<input id="mail-check-input" class="mail-check-input" style="height:34px;" disabled="disabled">
						<input type="button" id="email_button" class="btn btn-info" onclick="emailAuthorize()" value="이메일 보내기"  disabled="disabled">
						<span style="alert-message" class="mail-check-input-box-warn" id="MyTimer"/>
					</div>	
					<div>
						<span style="display:inline-block; alert-message; height:15px;" class="mail-check-input-box-warn" id="authorize-alert"/>
					</div>	
				</div>
	
			<!-- form 안에 버튼이 두개 일때 https://goodsgoods.tistory.com/274 -->
			<button class="btn btn-primary" type="submit">가입하기</button>
		</form> 
	</div>
</body>
</html>