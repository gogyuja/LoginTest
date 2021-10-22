package dao.Impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import dao.UserDAO;
import vo.UserVO;

@Repository("UserDAO")
public class UserDAOImpl implements UserDAO {

	@Resource(name="sqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory;
	
	private static final String userMapper = "mapper.User_SQL.";
	
	@Override
	public int joinUser(UserVO userVO) throws Exception {
		// TODO Auto-generated method stub
		return sqlSessionFactory.openSession().insert(userMapper+"insertUser",userVO);
	}

	@Override
	public UserVO loginUser(UserVO userVO) throws Exception {
		// TODO Auto-generated method stub
		return sqlSessionFactory.openSession().selectOne(userMapper+"selectUser", userVO);
	}

	@Override
	public int idCheck(String id) throws Exception{
		return sqlSessionFactory.openSession().selectOne(userMapper+"idCheck",id);
	}
	
	@Override
	public int pwCheck(UserVO userVO) throws Exception {
		return sqlSessionFactory.openSession().selectOne(userMapper+"pwCheck", userVO);
	}

	@Override
	public int nicknameCheck(String nickname) throws Exception {
		// TODO Auto-generated method stub
		return sqlSessionFactory.openSession().selectOne(userMapper+"nicknameCheck",nickname);
	}

	@Override
	public int emailCheck(String email) throws Exception {
		// TODO Auto-generated method stub
		return sqlSessionFactory.openSession().selectOne(userMapper+"emailCheck",email);
	}

	@Override
	public int kakaoidCheck(String id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSessionFactory.openSession().selectOne(userMapper+"kakaoidCheck",id);
	}
	
	@Override
	public UserVO kakaoidLogin(String id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSessionFactory.openSession().selectOne(userMapper+"kakaoidLogin",id);
	}


	@Override
	public int modifyUser(UserVO userVO) throws Exception {
		// TODO Auto-generated method stub
		return sqlSessionFactory.openSession().insert(userMapper+"modifyUser", userVO);
	}

	@Override
	public int deleteUser(UserVO userVO) throws Exception {
		// TODO Auto-generated method stub
		return sqlSessionFactory.openSession().delete(userMapper+"deleteUser", userVO);
	}

	@Override
	public int deletekakaoUser(String id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSessionFactory.openSession().delete(userMapper+"deletekakaoUser", id);
	}


}
