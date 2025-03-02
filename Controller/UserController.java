package com.ProjectManagement.Application.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProjectManagement.Application.Config.JwtProvider;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired 
	UserService uservice;
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception
	{

		User user=uservice.findUserProfileByJwt(jwt);
		//String email=JwtProvider.getEmailFromToken(jwt);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
}
