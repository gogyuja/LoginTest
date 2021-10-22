package vo;

import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

@Component
public class UserVO {
	private String platform;
	@Pattern(regexp="^[0-9a-z]{4,12}$",message = "아이디 정규식 위반")
	private String id;
	private String pw;
	@Pattern(regexp="^[0-9a-z가-힣]{4,8}$",message="닉네임 정규식 위반")
	private String nickname;
	private int gender;
	@Pattern(regexp="^([\\w\\.\\_\\-])*[a-zA-Z0-9]+([\\w\\.\\_\\-])*([a-zA-Z0-9])+([\\w\\.\\_\\-])+@([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{2,8}$",message="이메일 정규식 위반")
	private String email;
	
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
}
