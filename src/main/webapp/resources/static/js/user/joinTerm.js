//https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=javaking75&logNo=220136692493
function CheckForm(Join){
	var f= document.joinForm;
	var chk1=document.joinForm.check1.checked;
	var chk2=document.joinForm.check2.checked;
	
	if(!chk1){
		alert('회원가입약관에 동의해주세요.');
		//f.action="/user/join/termForm";
	}
	if(!chk2){
		alert('개인정보 수집 및 이용에 대한 약관에 동의해주세요');
		//f.action="/user/join/termForm";
	}
}

