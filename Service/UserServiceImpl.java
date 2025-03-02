package com.ProjectManagement.Application.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProjectManagement.Application.Config.JwtProvider;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo urepo; 
	
	@Override
	public User findUserProfileByJwt(String jwt) throws Exception {
		String email=JwtProvider.getEmailFromToken(jwt);
		if(email.isEmpty()) throw new Exception("Email not Found....");
		User user= urepo.findByEmail(email);
		if(user==null) throw new Exception("User not Found....");
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		User user=urepo.findByEmail(email);
	    if(user==null) throw new Exception("User not found");
		return user;
	}

	@Override
	public User findUserByid(Long userId) throws Exception {
		Optional<User> ouser=urepo.findById(userId);
		if(ouser.isEmpty()) throw new Exception("User not found"+userId);
		return ouser.get();
	}

	@Override
	public User setProjectSize(User user, int number) throws Exception {
		user.setProjectSize(user.getProjectSize()+number);
		return urepo.save(user);
	}

}
