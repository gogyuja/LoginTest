package service.Impl;

import service.UserService;
import vo.UserVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDAO;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;

	@Override
	public int join(UserVO userVO){
		int result=0;
		try {
			result = userDAO.joinUser(userVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public UserVO login(UserVO userVO) {
		UserVO userVO2 = null;
		try {
			userVO2=userDAO.loginUser(userVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userVO2;
	}
	
	public int idCheck(String id) {
		int result=1;
		try {
			result=userDAO.idCheck(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public int pwCheck(UserVO userVO) {
		int result=0;
		try {
			result=userDAO.pwCheck(userVO);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int nicknameCheck(String nickname) {
		int result=-1;
		try {
			result=userDAO.nicknameCheck(nickname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int emailCheck(String email) {
		int result=1;
		try {
			result=userDAO.emailCheck(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int kakaoidCheck(String id) {
		int result=-1;
		try {
			result=userDAO.kakaoidCheck(id);
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	@Override
	public UserVO kakaoidLogin(String id) {
		UserVO userVO = null;
		try {
			userVO=userDAO.kakaoidLogin(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userVO;
	}

	@Override
	public int modifyUser(UserVO userVO) {
		int result=0;
		try {
			result=userDAO.modifyUser(userVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int deleteUser(UserVO userVO) {
		int result=0;
		try {
			result=userDAO.deleteUser(userVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int deletekakaoUser(String id) {
		int result=0;
		try {
			result=userDAO.deletekakaoUser(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
}	