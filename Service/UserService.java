package com.ProjectManagement.Application.Service;

import com.ProjectManagement.Application.Model.User;

public interface UserService {
	
	User findUserProfileByJwt(String jwt) throws Exception;
	
	User findUserByEmail(String email) throws Exception;
	
	User findUserByid(Long userId) throws Exception;
	
	User setProjectSize(User user,int number) throws Exception;
}
