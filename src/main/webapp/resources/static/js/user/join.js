let idreg = /^[0-9a-z]{4,12}$/;//id식별을 위한 자바스크립트정규식 4~12자 숫자0~9 소문자a~z조합
let pwreg = /^(?=.*[!@#$%^&*()?])[0-9ㄱ-힣a-zA-Z!@#$%^&*()?]{8,12}$/;//패스워드정규식 8~12특수문자한자이상무조건포함
let nickreg = /^[0-9a-z가-힣]{4,8}$/;//닉네임정규식 숫자0~9,소문자a~z,한글가-힣조합
// 이메일 정규식 참조 : https://aspdotnet.tistory.com/2188
let emailreg = /^([\w\.\_\-])*[a-zA-Z0-9]+([\w\.\_\-])*([a-zA-Z0-9])+([\w\.\_\-])+@([a-zA-Z0-9]+\.)+[a-zA-Z0-9]{2,8}$/;
//emailAuthorize()메소드를 통해 서버로부터 받은 해당 이메일의 메일코드
let code="";

$(function(){
	var formElem=document.querySelector('form');
	formElem.onsubmit=submitForm;

	$("#id").on('keyup',idCheck);
	$("#pw").on('keyup',pwCheck);
	$("#pw2").on('blur',pwCheck2);
	$("#nickname").on('keyup',nicknameCheck);
	$("#email").on('keyup',emailCheck);
	$("#mail-check-input").on('keyup',compareAuthorize);

});

//회원이 입력한 아이디가 올바른지 확인
function idCheck(){
	var id=$("#id").val();
	
	if(!idreg.test(id)){
		//정규식불통
		$('#id-alert').css('color','red');
		$('#id-alert').html("아이디는 4~12자. 숫자 혹은 영어소문자 조합이어야합니다.");
		return false;
	}
	
	//중복된 아이디가 있는지없는지 확인
	$.ajax({
		url:'/user/idcheck/'+id,
		type:'get',
		dataType:false,
		processData:false,
		contentType:false,
		success:function(data){
			if(data==0){
				//중복된 아이디가 없다
				$('#id-alert').css('color','blue');
				$('#id-alert').html("사용할 수 있는 아이디입니다.");
			}else{
				//중복된 아이디가 있다.
				$('#id-alert').css('color','red');
				$('#id-alert').html("사용할수 없는 아이디입니다.")
			}
		}
	});
}

//비밀번호 정규식 체크
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

//비밀번호 재확인
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
	
	//회원 닉네임 중복체크
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

//이메일 체크
function emailCheck(){
	var email=$("#email").val();
	var email_button=$("#email_button");
	
	//이메일 정규식 체크
	if(!emailreg.test(email)){
		$('#email-alert').css('color','red');
		$('#email-alert').html("올바른 이메일 형식으로 입력해주세요.");
		//정규식을 통과하지 못하면 "이메일 보내기"버튼은 사용할수없다.
		email_button.attr("disabled",true);
		return false;
	}
	
	//해당 이메일이 중복되는지 아닌지확인
	$.ajax({
		// email뒤에 '//'은 이메일의 @뒤로 url이 인식이 되지않아 붙였다.
		url:'/user/emailcheck/'+email+'//',
		type:'get',
		dataType:false,
		processData:false,
		contentType:false,
		success:function(data){
			if(data==0){
				$('#email-alert').css('color','blue');
				$('#email-alert').html("사용할 수 있는 이메일입니다.");
				email_button.attr("disabled",false);
			}else{
				$('#email-alert').css('color','red');
				$('#email-alert').html("사용할수 없는 이메일입니다.");
				email_button.attr("disabled",true);
			}
		}
	});
}

//"이메일 보내기" 버튼누르기
function emailAuthorize(){
	//이메일
	var email=$("#email").val();
	//이메일 인증번호를 치는 체크박스
	var checkBox=$(".mail-check-input");
	//"이메일 보내기" 버튼	
	var email_button=$("#email_button");
	
	//한번 누르고 이메일인증버튼을 중복해서 누르지 못하도록 막는다.
	email_button.attr("disabled",true);
	
	$.ajax({
		//서버에 해당 이메일 값으로 인증번호를 보내도록요청한다
		url:'/user/emailAuthorize/'+email+'//',
		type:'get',
		dataType:false,
		processData:false,
		contentType:false,
		success:function(data){
			//서버에서 이메일로 메일을 보내면 이메일 인증번호를 치는 체크박스의 disabled 속성을 해제한다.
			checkBox.attr("disabled",false);
			//console.log("이메일 인증코드"+data);
			code=data;
			//이메일 인증은 3분간 유효하다
			var minutes = 3;
			var threeMinute = (60 * minutes) - 1,display = document.querySelector('#MyTimer');
			startTimer(threeMinute, display);
		},
		
		error:function(request){
			//에러가 났다면 이메일 인증버튼을 다시 누를 수 있도록 
			email_button.attr("disabled",false);
			
			if(request.status==404){
				alert("정상적인 방법으로 인증해주십시오");
			}else{
				alert("에러가 났습니다. 재시도 해주십시오.")
			}
		}
	});
}

//"이메일 보내기"버튼을 눌렀을 때 인증번호가 3분간 유효하게 해준다.
function startTimer(duration, display) {
	
	  var timer = duration, minutes, seconds;
	
	  var interval = setInterval(function () {
	    minutes = parseInt(timer / 60, 10)
	    seconds = parseInt(timer % 60, 10);

	    minutes = minutes < 10 ? "0" + minutes : minutes;
	    seconds = seconds < 10 ? "0" + seconds : seconds;

	    display.textContent = minutes + ":" + seconds;

	    if (--timer < 0) {
	      timer = duration;
	    }
	    if(timer === 0) {
	      clearInterval(interval);
	      display.textContent = "다시 인증을 요청하세요!";
	      //인증번호를 초기화한다.
	      code='';
		  
	      //이메일인증버튼을 다시 누를수있게 "이메일 보내기"버튼의 disabled속성을 해지한다
		  var email_button=$("#email_button");
		  email_button.attr("disabled",false);
	    }
	  }, 1000);
}

//사용자가 이메일을 통해 받은 인증번호를 입력하면 해당인증메일이 맞는지 안맞는지 확인하는 메소드
function compareAuthorize(){
	var inputCode=$(".mail-check-input").val();
	
	if(inputCode==''){
		//서버에서 발급한 인증번호를 담는 변수인 code의 초기값은 ''이다. 그러므로 ''가 정답일수는 없다.
		$("#authorize-alert").html("인증번호를 다시 보내주세요");
		$("#authorize-alert").attr("class","incorrect");
	}
	else if(inputCode==code){
		$("#authorize-alert").html("인증번호가 일치합니다.");
		$("#authorize-alert").attr("class","correct");
	}else{
		$("#authorize-alert").html("인증번호를 다시 확인해주세요");
		$("#authorize-alert").attr("class","incorrect");
	}
}

//제출메소드
function submitForm(){
	var id=$("#id").val();
	var pw=$("#pw").val();
	var pw2=$("#pw2").val();
	var nickname=$("#nickname").val();
	var email=$("#email").val();
	var inputCode=$(".mail-check-input").val();
	
	if(!idreg.test(id)){
		alert("아이디를 다시 입력하세요");
		return false;
	}	
	else if(!pwreg.test(pw)){
		alert("비밀번호를 다시 입력하세요");
		return false;
	}else if(pw!=pw2){
		alert("비밀번호가 일치하지 않습니다. 다시입력해주세요");
		return false;
	}
	else if(!nickreg.test(nickname)){
		alert("닉네임을 다시 입력하세요");
		return false;
	}else if(!emailreg.test(email)){
		alert("이메일을 다시 입력하세요");
		return false;
	}else if(code==""){
		//html을 조작해 disabled를 해제시킨 뒤 공백으로 했을 경우를 대비
		alert("이메일을 인증하세요.");
		return false;
	}
	else if(inputCode!=code){
		alert("인증번호가 일치하지 않습니다. 다시 입력하세요")
		return false;
	}
}