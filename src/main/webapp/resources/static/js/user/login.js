function kakaoLogin(){
	var w=600;
	var h=800;
	var x=(screen.width-w)/2;//가로화면 가운데
	var y=(screen.height-h)/2;//세로화면 가운데
	window.open("https://kauth.kakao.com/oauth/authorize?client_id=bd2bfec7dc12e96624279aa82860fbca&redirect_uri=http://localhost:8080/kakao/login&response_type=code","kakao",'left='+x+',top='+y+',width='+w+',height='+h);;
}