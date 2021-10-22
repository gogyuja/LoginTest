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
	<div class="container">
	<form action="/user/modifyuserForm" method="post">
		<h1>개인정보 수정을 위해 비밀번호를 다시 입력해주세요.</h1>
			<div class="col-xs-6">
				<label for="pw">비밀번호 :</label>
	  			<input type="password" class="form-control" id="pw" name="pw">
	  			<button type="submit" class="btn btn-primary">비밀번호 확인</button>
  			</div>
	</form>
	</div>
</body>
</html>