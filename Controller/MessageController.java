package com.ProjectManagement.Application.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProjectManagement.Application.Model.Chat;
import com.ProjectManagement.Application.Model.Message;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Request.MessageRequest;
import com.ProjectManagement.Application.Response.MessageResponse;
import com.ProjectManagement.Application.Service.MessageService;
import com.ProjectManagement.Application.Service.ProjectService;
import com.ProjectManagement.Application.Service.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	
	@Autowired
	MessageService mservice;
	@Autowired
	UserService uservice;
	@Autowired
	ProjectService pservice;
	
	@GetMapping("/{projectId}")
	public ResponseEntity<List<Message>> getMessages(
			@PathVariable Long projectId
			) throws Exception{
		
		List<Message> messages=mservice.getMessagesByProjectId(projectId);
		return new ResponseEntity<>(messages,HttpStatus.OK);
	}
	
	@PostMapping("/send")
	public ResponseEntity<Message> sendMessage(
			@RequestBody MessageRequest mesreq) throws Exception{
		
//		User user= uservice.findUserProfileByJwt(jwt);
		User user=uservice.findUserByid(mesreq.getSenderId());
		Chat chat=pservice.getChat(mesreq.getProjectId());
		if(chat==null || user==null) throw new Exception("Invalid Ids");
		
		Message message=mservice.sendMessage(mesreq.getContent(), mesreq.getSenderId(), mesreq.getProjectId());
		
		return new ResponseEntity<>(message,HttpStatus.OK);
		
		
	}
	
	@DeleteMapping("/{messageId}")
	public ResponseEntity<MessageResponse> deleteMessage(
			@PathVariable Long messageId,
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user= uservice.findUserProfileByJwt(jwt);
		mservice.deleteMessage(user.getId(), messageId);
		
		MessageResponse mes=new MessageResponse("Message deleted succcessfully");
		return new ResponseEntity<>(mes,HttpStatus.OK);
		
	}
	
	
	
	

}
