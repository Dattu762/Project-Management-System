package com.ProjectManagement.Application.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Repo.UserRepo;

@Service
public class CustomUserdetailsServiceimpl implements UserDetailsService {

	@Autowired
	private UserRepo urepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user=urepo.findByEmail(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("User not found with thie email"+username);
		}
		List<GrantedAuthority> authorities=new ArrayList<>();
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}
