package com.ProjectManagement.Application.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender javamailsender;
	
	@Autowired
    public EmailServiceImpl(JavaMailSender javamailsender) {
        this.javamailsender = javamailsender;
    }
	
	@Override
	public void SendEmailWithToken(String UserEmail, String link) throws MessagingException {
		// TODO Auto-generated method stub
		MimeMessage mimemessage=javamailsender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mimemessage,"utf-8");
		
		String subject="Join Project Team Invitation";
		String Text="Click the link to join the team  \n"+link;
		
		helper.setSubject(subject);
		helper.setText(Text,true);
//		helper.
		helper.setTo(UserEmail);
		
		try {
			
			javamailsender.send(mimemessage);	
			
		} catch(MailSendException e) {
			// TODO: handle exception
			throw new MailSendException("Failed to send email");
			
		}
		
	}

}
