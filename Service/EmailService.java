package com.ProjectManagement.Application.Service;

import jakarta.mail.MessagingException;

public interface EmailService {
	
	void SendEmailWithToken(String UserEmail, String link) throws MessagingException;
}
