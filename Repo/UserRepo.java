package com.ProjectManagement.Application.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProjectManagement.Application.Model.User;

public interface UserRepo extends JpaRepository<User,Long> {
	
	User findByEmail(String email);
}
