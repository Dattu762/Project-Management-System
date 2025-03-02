package com.ProjectManagement.Application.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProjectManagement.Application.Model.Chat;
import com.ProjectManagement.Application.Model.Message;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Repo.ChatRepo;
import com.ProjectManagement.Application.Repo.MessageRepo;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageRepo mrepo;
	@Autowired
	UserService uservice;
	@Autowired
	ChatService cservice;
	@Autowired
	ProjectService pservice;
	
	
	@Override
	public Message sendMessage(String content, Long userId, Long projectId) throws Exception {
		
		Message nmessage=new Message();
		//Chat chat=cservice.getChatById(chatId);
		Chat chat=pservice.getChat(projectId);
		
		nmessage.setContent(content);
		nmessage.setChat(chat);
		nmessage.setSender(uservice.findUserByid(userId));
		nmessage.setCreatedAt(LocalDateTime.now());
		
		Message Smessage=mrepo.save(nmessage);
		chat.getMessages().add(Smessage);
		
		return Smessage;
	}

	@Override
	public void deleteMessage(Long userId, Long messageId) throws Exception {
		
		User user=uservice.findUserByid(userId);
		Optional<Message> message=mrepo.findById(messageId);
		if(message.isEmpty()) throw new Exception("Message not found");
		
		if(message.get().getSender().getId()==user.getId()) mrepo.delete(message.get());
		else throw new Exception("Unauthorised access to delete message"+message.get());
	}

	@Override
	public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
		Chat chat=pservice.getChat(projectId);
		List<Message> messages=mrepo.findByChatIdOrderByCreatedAtAsc(chat.getId());
		
		return messages;
	}

}
