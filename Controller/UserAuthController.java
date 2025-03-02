package com.ProjectManagement.Application.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ProjectManagement.Application.Config.JwtProvider;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Repo.UserRepo;
import com.ProjectManagement.Application.Request.UserRequest;
import com.ProjectManagement.Application.Response.AuthResponse;
import com.ProjectManagement.Application.Service.CustomUserdetailsServiceimpl;
import com.ProjectManagement.Application.Service.SubscriptionService;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserdetailsServiceimpl usimpl;
	
	@Autowired
	private SubscriptionService subservice;
	
	User u=new User();
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws Exception{
		User isUserexists=repo.findByEmail(user.getEmail());
		if(isUserexists!=null) {
			throw new Exception("The User already Exists.."+isUserexists.getEmail());
		}
		User CreatedUser=new User();
		CreatedUser.setEmail(user.getEmail());
		CreatedUser.setFullname(user.getFullname());
		CreatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser=repo.save(CreatedUser);
		
		subservice.createSubscription(savedUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt=JwtProvider.generateToken(authentication);
		
		AuthResponse res=new AuthResponse();
		res.setMessage("Signup Successful");
		res.setJwt(jwt);
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody UserRequest userRequest){
		String email=userRequest.getEmail();
		String password=userRequest.getPassword();
		
		Authentication authentication=authenticate(email,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt=JwtProvider.generateToken(authentication);
		
		AuthResponse res=new AuthResponse();
		res.setMessage("Signin Successful");
		res.setJwt(jwt);
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
		
		
	}

	private Authentication authenticate(String email, String password) {
		UserDetails ud=usimpl.loadUserByUsername(email);
		if(ud==null) throw new BadCredentialsException("The Email is not available");
		if(!passwordEncoder.matches(password, ud.getPassword())) throw new BadCredentialsException("The Password is Wrong!!");
		
		return new UsernamePasswordAuthenticationToken(ud,null,ud.getAuthorities());
	}
}
