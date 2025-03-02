package com.ProjectManagement.Application.Service;

import com.ProjectManagement.Application.Model.Invitation;

public interface InvitationService {
	
	public void sendInvitation(String email, Long projectId) throws Exception;
	
	public Invitation accceptInvitation(String token, Long userId) throws Exception;
	
	public String getTokenByUserEmail(String userEmail) throws Exception;
	
	void deleteToken(String token) throws Exception;
}
