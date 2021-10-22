package dao;

import vo.UserVO;

public interface UserDAO {
	//userVO를 통해 받은 값을 저장
	public int joinUser(UserVO userVO)throws Exception;
		
	public UserVO loginUser(UserVO userVO)throws Exception;
	
	public int idCheck(String id) throws Exception;
	
	public int pwCheck(UserVO userVO)throws Exception;
	
	public int kakaoidCheck(String id)throws Exception;
	
	public UserVO kakaoidLogin(String id)throws Exception;
	
	public int nicknameCheck(String nickname)throws Exception;
	
	public int emailCheck(String email)throws Exception;

	public int modifyUser(UserVO userVO)throws Exception;
	
	public int deleteUser(UserVO userVO)throws Exception;
	
	public int deletekakaoUser(String id)throws Exception;
}
