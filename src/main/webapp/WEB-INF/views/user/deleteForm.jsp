<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<jsp:include page="../top.jsp" />
	<div class="container">
	<form action="/user/delete" method="post">
		<h1>회원탈퇴하면 계정이 즉시 사라지고 해당계정으로 써놨던 글은 남아있습니다 머라머0왈ㄹ루라롸루라ㅜ라 탈퇴하려면 비밀번호 입력하세요.
		국가는 노인과 청소년의 복지향상을 위한 정책을 실시할 의무를 진다. 체포·구속·압수 또는 수색을 할 때에는 적법한 절차에 따라 검사의 신청에 의하여 법관이 발부한 영장을 제시하여야 한다. 다만, 현행범인인 경우와 장기 3년 이상의 형에 해당하는 죄를 범하고 도피 또는 증거인멸의 염려가 있을 때에는 사후에 영장을 청구할 수 있다.
		</h1>
		<c:if test="${sessionScope.platform eq 'normal' }">
			<div class="col-xs-6">
				<label for="pw">비밀번호 :</label>
	  			<input type="password" class="form-control" id="pw" name="pw">
	 		</div>
	 	</c:if>
	 	<button type="submit" class="btn btn-primary">확인</button>
	</form>
	</div>
</body>
</html>