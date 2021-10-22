function normalJoin(){
	//일반회원가입 요청
	var form =document.joinForm;
	form.action="/user/joinForm"
	form.submit();
}

function kakaoJoin(){
	//카카오연동회원가입 요청
	var form=document.joinForm;
	form.action="https://kauth.kakao.com/oauth/authorize?client_id=bd2bfec7dc12e96624279aa82860fbca&redirect_uri=http://localhost:8080/kakao/login&response_type=code";
	form.submit();
}