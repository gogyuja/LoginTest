let pwreg = /^(?=.*[!@#$%^&*()?])[0-9ㄱ-힣a-zA-Z!@#$%^&*()?]{8,12}$/;//패스워드정규식 8~12특수문자한자이상무조건포함
let nickreg = /^[0-9a-z가-힣]{4,8}$/;//닉네임정규식 숫자0~9,소문자a~z,한글가-힣조합

$(function(){
	var formElem=document.querySelector('form');
	formElem.onsubmit=submitForm;

	$("#pw").on('keyup',pwCheck);
	$("#pw2").on('blur',pwCheck2);
	$("#nickname").on('keyup',nicknameCheck);	
});



function pwCheck(){
	var pw=$("#pw").val();
	
	if(pwreg.test(pw)){
		$('#pw-alert').html("");
	}else{
		//정규식불통
		$('#pw-alert').css('color','red');
		$('#pw-alert').html("비밀번호는 8~12자리 특수문자를 하나이상포함해야합니다.");
		return false;
	}
}

function pwCheck2(){
	var pw=$("#pw").val();
	var pw2=$("#pw2").val();
	if(pw==pw2){
		$('#pw2-alert').html("");
	}else{
		$('#pw2-alert').css('color','red');
		$('#pw2-alert').html("비밀번호가 동일하지 않습니다. 다시 확인해주세요");
	}

}

//회원이 입력한 닉네임이 올바른 지 확인
function nicknameCheck(){
	var nickname=$("#nickname").val();

	if(!nickreg.test(nickname)){
		//정규식 불통
		$('#nickname-alert').css('color','red');
		$('#nickname-alert').html("닉네임은 4~8자. 영어소문자 한글 숫자만 가능합니다.");
		return false;
	}
	
	$.ajax({
		url:'/user/nicknamecheck/'+nickname,
		type:'get',
		dataType:false,
		processData:false,
		contentType:false,
		success:function(data){
			if(data==0){
				$('#nickname-alert').css('color','blue');
				$('#nickname-alert').html("사용할 수 있는 닉네임입니다.");
			}else{
				$('#nickname-alert').css('color','red');
				$('#nickname-alert').html("사용할수 없는 닉네임입니다.")
			}
		}
	});
}

function submitForm(){
	var pw=$("#pw").val();
	var pw2=$("#pw2").val();
	var nickname=$("#nickname").val();	

	if(!pwreg.test(pw)){
		alert("비밀번호를 다시 입력하세요");
		return false;
	}else if(pw!=pw2){
		alert("비밀번호가 일치하지 않습니다. 다시입력해주세요");
		return false;
	}
	else if(!nickreg.test(nickname)){
		alert("닉네임을 다시 입력하세요");
		return false;
	}
}


