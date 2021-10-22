let nickreg = /^[0-9a-z가-힣]{4,8}$/;//닉네임정규식 숫자0~9,소문자a~z,한글가-힣조합

$(function(){
	var formElem=document.querySelector('form');
	formElem.onsubmit=submitForm;

	$("#nickname").on('keyup',nicknameCheck);	
});


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

	var nickname=$("#nickname").val();	


	if(!nickreg.test(nickname)){
		alert("닉네임을 다시 입력하세요");
		return false;
	}
}


