package service;

import vo.UserVO;

public interface UserService {
	public int join(UserVO userVO);
	
	public UserVO login(UserVO userVO);
	
	public int idCheck(String id);
	
	public int pwCheck(UserVO userVO);
	
	public int kakaoidCheck(String id);
	
	public UserVO kakaoidLogin(String id);
	
	public int nicknameCheck(String nickname);
	
	public int emailCheck(String email);
	
	public int modifyUser(UserVO userVO);
	
	public int deleteUser(UserVO userVO);
	
	public int deletekakaoUser(String id);
	
}
