package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.UserDAO;
import service.UserService;
import service.Impl.KakaoService;
import vo.UserVO;

@PropertySource("/WEB-INF/config/mail.properties")
@Controller
public class UserController {

	@Autowired
	UserVO userVO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	UserService userService;
	
	@Autowired
	KakaoService kakaoService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	//이메일 보내기 보내는 이메일 주소
	@Value("${username}")
	String setFrom;	
	
	//회원가입 동의 페이지로 이동
	@GetMapping("/user/join/termForm")
	public String jointermForm(HttpServletRequest request, HttpServletResponse response,Model model) {		
		//약관동의여부확인
		String check1=request.getParameter("check1");
		String check2=request.getParameter("check2");
		model.addAttribute("check1", check1);
		model.addAttribute("check2",check2);
		return "/user/joinTerm";
	}
	
	//회원가입 종류페이지로 이동 1.일반회원가입 2.카카오회원가입 3.네이버회원가입이있다.
	@GetMapping("/user/join/kinds")
	public String joinKinds(HttpServletRequest request, HttpServletResponse response,Model model) {
		//이전페이지인 회원약관동의에 대해 체크여부확인
		String check1=request.getParameter("check1");
		String check2=request.getParameter("check2");	
		model.addAttribute("check1", check1);
		model.addAttribute("check2",check2);
		
		//만약 이용약관에 동의하지 않았다면 이용약관동의 페이지로 이동시킨다.
		if(check1!=null&&check2!=null) {
			//약관에 모두 동의했다.회원가입 종류페이지로 이동.
			return "/user/joinKinds";
		}else {
			//약관에 모두 동의하지 않았다.
			return "/user/joinTerm";
		}
	}
	
	//일반회원가입 페이지로 이동.
	@GetMapping("/user/joinForm")
	public String joinForm() {
		return "/user/joinForm";
	}
	
	//회원가입시 아이디의 중복여부체크
	@ResponseBody
	@GetMapping(value="/user/idcheck/{id}")
	public int checkUserid(@PathVariable String id,HttpServletRequest request,HttpServletResponse response) {
		//0 유저가 입력한 아이디가 이전에 존재하지 않음. 사용가능
		//1 유저가 입력한 아이디가 이미 존재함 사용불가

		int result =userService.idCheck(id);	
		return result;	
	}
	
	//회원가입시 닉네임 중복여부체크
	@ResponseBody
	@GetMapping(value="/user/nicknamecheck/{nickname}")
	public int checkUsernickname(@PathVariable String nickname,HttpServletRequest request,HttpServletResponse response) {
	
		int result=userService.nicknameCheck(nickname);
		return result;
	}
	
	//회원가입시 이메일 중복여부체크
	@ResponseBody
	@GetMapping(value="/user/emailcheck/{email}")
	public int checkUseremail(@PathVariable String email,HttpServletRequest request,HttpServletResponse response) {
		int result=userService.emailCheck(email);
		return result;
	}

	@ResponseBody
	@GetMapping(value = "/user/emailAuthorize/{email}")
	public ResponseEntity<Integer> mailSending(HttpServletRequest request, HttpServletResponse response,@PathVariable String email) throws IOException {
		/*
		 *어떤 식으로든 이메일 형식을 취했을 때 처리는 하지 못했다.
		 *예를 들어 qwert1234@nonono.nono 이런식으로 있지도 않은 사이트를 넣었을 때 메일을 발송하는 구글메일에서는 에러를 띄우지만,
		 *자바코드는 정상적으로 checkNum 숫자를 프론트로 넘어간다.
		 */
		
		//인증번호(난수) 생성
		Random random=new Random();
		Integer checkNum=random.nextInt(999999)+000000;

		String toMail=email;
		String title="회원가입 인증 이메일입니다.";
		String content=
				"밑에 입력된 번호를 회원가입 페이지에 입력해주십시오."+
				"<br><br>"+
				"인증번호는 "+checkNum+"입니다.";
		
		/*
		 *DB내에서 이전에 해당 메일로 등록된 메일이 있는 지 없는 지 체크한다. 결과가 0이어야 이전에 등록되지 않은 이메일이다.
		 *해당 검증식에서 걸린다는 것은 허용되지 않는 방법(405)을 통해 서버로 요청을 보낸 것이다.
		*/
		int result=userService.emailCheck(email);
		if(result!=0) {
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}
		
		
		try {
			MimeMessage message=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message,true,"utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content,true);
			mailSender.send(message);
		}catch(Exception e) {
			
			/*
			 * 허용되지 않는 방법으로 요청했을 때 프론트에서 자바스크립트를 통해 일차적으로 검증하지면 html을 조작하는 방법을 통해 요청이 들어올수있다.
			 * 그럴 때 에러처리. 예를 들어 qwer123 만 달랑 보냈을때
			 */
			e.printStackTrace();
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Integer>(checkNum,HttpStatus.OK);
	}
	
	//일반회원가입.
	@PostMapping("/user/join")
	public String join(HttpServletRequest request, HttpServletResponse response,@ModelAttribute(name = "joinForm") @Valid UserVO joinForm,BindingResult result) {
		
		//유효성 검사에서 위반된 부분이 있다면
		if (result.hasErrors()) { 
			// 위반결과를 모두 가져온다.
			//지금은 그냥 주석처리 나중에 로그로 다 변경
			/*
			for (ObjectError obj : result.getAllErrors()) {
				System.out.printf("메시지 : %s\n", obj.getDefaultMessage());
				System.out.printf("code : %s\n", obj.getCode());
				System.out.printf("object name : %s\n", obj.getObjectName());
				System.out.println("--------------------------------");
			}*/
			return "user/joinForm";
		}
		
		//일반회원은 회원가입 시 platform 을 normal로 지정한다.
		joinForm.setPlatform("normal");
		userService.join(joinForm);
		
		//정상적인 회원가입 후 로그인 페이지로 이동
		return "/user/loginForm";
	}
	
	//로그인 페이지로 이동.
	@GetMapping("/user/loginForm")
	public String loginForm() {
		return "/user/loginForm";
	}
	
	//로그인.
	@PostMapping("/user/login")
	public String login(HttpServletRequest request, HttpServletResponse response,@ModelAttribute(name = "loginForm") UserVO loginForm,@RequestParam(value="code",required=false)String code) {
		
		UserVO user=userService.login(loginForm);
		
		if(user==null) {
			return "/user/login_fail";
		}
		
		
		HttpSession session = request.getSession();
		session.setAttribute("user", user.getId());
		session.setAttribute("platform", user.getPlatform());
		//정상적인 로그인 후 메인으로
		return "redirect:/";
	}
	
	//카카오로그인을 햇을때 RequestMapping을 다음과 같이 구성해주어야 KOE001 에러를 피할수있다.
	//https://gongcha.tistory.com/19?category=737124
	@RequestMapping(value="/kakao/login",produces = "application/json",method= {RequestMethod.GET,RequestMethod.POST})
	public String home(HttpServletRequest request,HttpServletResponse response, @RequestParam(value = "code", required = false) String code) throws Exception{		
		System.out.println("#########" + code);
        String access_Token = kakaoService.getAccessToken(code);
        System.out.println("###access_Token#### : " + access_Token);
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);      
        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###userInfo#### : " + userInfo.get("email"));
        System.out.println("###nickname#### : " + userInfo.get("nickname"));
        System.out.println("###profile_image#### : " + userInfo.get("profile_image"));
        
        //카카오 회원ID가 이전에 DB에 들어왔는지 안들어왔는지 확인
		/*
		 * int kakaoidCheck=userService.kakaoidCheck(userInfo.get("id").toString());
		 * if(kakaoidCheck==1) { System.out.println("이미 회원으로 등록되어잇음");
		 * 
		 * }else if(kakaoidCheck==0) { userVO.setId(userInfo.get("id").toString());
		 * 
		 * String tempnick=UUID.randomUUID().toString().replace("-", "");
		 * userVO.setNickname(tempnick);
		 * 
		 * if(userInfo.get("gender").equals("male")) { userVO.setGender(1); }else
		 * if(userInfo.get("gender").equals("female")) { userVO.setGender(0); }
		 * userVO.setEmail(userInfo.get("email").toString());
		 * userVO.setPlatform("kakao"); userService.join(userVO); }else
		 * if(kakaoidCheck==-1) { System.out.println("알수없는에러"); } HttpSession session =
		 * request.getSession(); session.setAttribute("user", userInfo.get("id"));
		 */
        
        UserVO userVO=userService.kakaoidLogin(userInfo.get("id").toString());
        if(userVO==null) {
        	userVO=new UserVO();
        	System.out.println("회원으로 등록시킨다.");
        	//이미 회원으로 등록되어있지 않다. 회원으로 등록시킨다.
        	 userVO.setId(userInfo.get("id").toString());
             
             String tempnick=UUID.randomUUID().toString().replace("-", "");          
             userVO.setNickname(tempnick);
             
             if(userInfo.get("gender").equals("male")) {
             	userVO.setGender(1);
             }else if(userInfo.get("gender").equals("female")) {
               	userVO.setGender(0);
             }
             userVO.setEmail(userInfo.get("email").toString());
             userVO.setPlatform("kakao");
             userService.join(userVO);
        }
        HttpSession session = request.getSession();
		session.setAttribute("user", userVO.getId());
		session.setAttribute("platform", userVO.getPlatform());
        
       
		//정상적인 카카오 소셜 로그인 후 클로즈jsp를 반환한다.
        return "/user/closeLoginform";	
	}
	
	//로그아웃
	@GetMapping("/user/logout")
	public String logOut(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		session.invalidate();
		return "/main";
	}
	
	//마이페이지로 이동
	@GetMapping("/user/myPage")
	public String myPage() {
		return "/user/myPage";
	}
	
	//내 정보를 수정하기 전 해당 유저가 진짜 유저인지 확인하기 위해 비밀번호를 다시 입력
	@GetMapping("/user/checkmodifyUser")
	public String checkmodifyUser(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("user");
		String platform=(String)session.getAttribute("platform");
		if(platform.equals("normal")) {
			return "/user/modifyuserCheck";
		}else {
			//카카오 회원가입일시
			 UserVO userVO=userService.kakaoidLogin(id);
			 //result_userVO를 모델로 넘겨준다.
			 model.addAttribute("UserVO", userVO);
			return "/user/modifyForm";
		}
	}
	
	//내 정보 수정으로 이동
	@PostMapping("/user/modifyuserForm")
	public String modifyUserForm(HttpServletRequest request, HttpServletResponse response,@RequestParam(required=false)String pw,Model model) {
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("user");
		String platform=(String)session.getAttribute("platform");
		userVO.setId(id);
		userVO.setPw(pw);
		//int result=userService.pwCheck(userVO);
		UserVO result_userVO;
		if(platform.equals("normal")) {
			 result_userVO=userService.login(userVO);
		}else{
			//카카오 회원가입일시
			 result_userVO=userService.kakaoidLogin(id);
			 //result_userVO를 모델로 넘겨준다.
			 model.addAttribute("UserVO", result_userVO);
		}
		
		if(result_userVO!=null) {
			model.addAttribute("UserVO", result_userVO);
			return "/user/modifyForm";
		}else {
			return "redirect: /user/checkmodifyUser";
		}
	}
	
	//회원정보 수정
	@PostMapping("/user/modify")
	public String modifyUser(HttpServletRequest request, HttpServletResponse response,@ModelAttribute(name = "joinForm")UserVO joinForm) {
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("user");
		joinForm.setId(id);
		
		int result=userService.modifyUser(joinForm);
		return "/user/myPage";
	}
	
	//회원탈퇴 페이지로 이동
	@GetMapping("/user/deleteuserForm")
	public String deleteuserform(HttpServletRequest request,Model model) {		
		HttpSession session = request.getSession();
		String id=(String)session.getAttribute("user");
		String platform=(String)session.getAttribute("platform");
		model.addAttribute("id", id);
		model.addAttribute("platform",platform);
		
		
		return "/user/deleteForm";
	}
	
	//회원탈퇴
	@PostMapping("/user/delete")
	public String termination(HttpServletRequest request, @RequestParam(required=false)String pw) {
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("user");
		String platform=(String)session.getAttribute("platform");
		userVO.setId(id);
		userVO.setPw(pw);
		
		int result=-9999;
		
		if(platform.equals("normal")) {
			result = userService.deleteUser(userVO);
		}else {
			//플랫폼이 소셜 플랫폼일시 DB에서 해당 유저 삭제
			result=userService.deletekakaoUser(id);
		}
		
		if(result==1) {
			session.invalidate();
		}else {
			//회원탈퇴 실패
			return "/user/wrong_password";
		}
		return "/main";
	}
	
	@GetMapping("/user/not_login")
	public String not_login() {
		return "user/not_login";
	}
}
