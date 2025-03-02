package com.ProjectManagement.Application.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProjectManagement.Application.Model.Invitation;
import com.ProjectManagement.Application.Repo.InvitationRepo;

@Service
public class InvitationServiceImpl implements InvitationService {
	@Autowired
	InvitationRepo irepo;
	@Autowired
	EmailService eservice;
	
	@Override
	public void sendInvitation(String email, Long projectId) throws Exception {		
		String Itoken=UUID.randomUUID().toString();
		
		Invitation invitation=new Invitation();
		invitation.setToken(Itoken);
		invitation.setEmail(email);
		invitation.setProjectId(projectId);
		
		irepo.save(invitation);
		
		String ilink="http://localhost:3000/accept-invitation?token="+Itoken; // we need add the frontend url and after that we need to pass the token
		
		eservice.SendEmailWithToken(email, ilink);
		
		
	}

	@Override
	public Invitation accceptInvitation(String token, Long userId) throws Exception {
		Invitation invitation=irepo.findByToken(token);
		if(invitation==null) {
			throw new Exception("Invalid Invitation Token");
		}
		return invitation;
	}

	@Override
	public String getTokenByUserEmail(String userEmail) throws Exception {
		Invitation invitation=irepo.findByEmail(userEmail);		
		return invitation.getToken();
	}

	@Override
	public void deleteToken(String token) throws Exception {
		Invitation invitation=irepo.findByToken(token);
		irepo.delete(invitation); 

	}

}
