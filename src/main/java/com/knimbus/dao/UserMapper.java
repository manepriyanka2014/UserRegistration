package com.knimbus.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.knimbus.model.UserRole;
import com.knimbus.model.User;
import com.knimbus.util.config.MyBatisUtil;

@Repository
public class UserMapper {
	
	public void createUser(User user) {
		System.out.println("-----------------------Inside createUser-----------------");
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		session.insert("createUser", user);
		session.commit();
		session.close();
	}
	
	public User updateUser(User user){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        System.out.println("Input user:"+ user.toString());
		session.update("com.knimbus.dao.UserMapper.updateUser", user);
		User updatedUser = (User) session.selectOne("com.knimbus.dao.UserMapper.getById", user.getUserId());
	    System.out.println("Details of the user after update operation" );
	    System.out.println(updatedUser.toString());   
	    
		session.commit();
		session.close();
    
	    return updatedUser;
	}
	
	public User getUser(int userId){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
      
		User user = (User) session.selectOne("com.knimbus.dao.UserMapper.getById", userId);
	    System.out.println("Details of the user :" );
	    System.out.println(user.toString());   
	    
		session.commit();
		session.close();
    
	    return user;
	}
	
	public User getUserByEmailId(String email){
		System.out.println("Email:"+ email);
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
      
		User user = (User) session.selectOne("com.knimbus.dao.UserMapper.getUserByEmailId", email);
	    System.out.println("Details of the user :" );
	    if(user != null )
	    {
	    	System.out.println(user.toString());
	    }
	    else
	    {
	    	System.out.println("User Does not exist");
	    }
	    
		session.commit();
		session.close();
    
	    return user;
	}
	
	
	
	public void deleteUser(int userId){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		session.delete("deleteUser", userId);
		session.commit();
		session.close();
	}
	
	
	public List<User> getAllUser(){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		System.out.println("-----------------------Inside All user list-----------------");
		@SuppressWarnings("unchecked")
		List<User> userList = session.selectList("getAllUser");
		session.commit();
		session.close();
		System.out.println("User Count"+userList.size());
		return userList;
	}
	

	public String loginUser(User user ) {
		//System.out.println(user.getUserId()+"--"+ user.getPassword() );
		System.out.println(user.getEmail()+"--"+ user.getPassword() );
		String roleName = "";
		System.out.println("-----------------------Inside loginUser-----------------");
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		User user1 = (User)session.selectOne("com.knimbus.dao.UserMapper.validateUserCredential", user);
		System.out.println(user1.toString());
		System.out.println(user1.getUserId());
		UserRole role = (UserRole)session.selectOne("validateUserRole", user1.getUserId());
		if( role != null)
		{
		  System.out.println(role.toString());
		  roleName= role.getRoleName();
		}
		
		//System.out.println(role.getRoleName());
		session.commit();
		session.close();
		return roleName;
		
	}
}
