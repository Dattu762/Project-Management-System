package com.ProjectManagement.Application.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProjectManagement.Application.Model.Invitation;
import java.util.List;


public interface InvitationRepo extends JpaRepository<Invitation, Long> {

	Invitation findByToken(String token);
	
	Invitation findByEmail(String userEmail);
}
