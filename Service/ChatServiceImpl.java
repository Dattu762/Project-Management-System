package com.ProjectManagement.Application.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProjectManagement.Application.Model.Chat;
import com.ProjectManagement.Application.Repo.ChatRepo;

@Service
public class ChatServiceImpl implements ChatService {
	@Autowired
	ChatRepo crepo;
	
	@Override
	public Chat createChat(Chat chat) {
		return crepo.save(chat);
	}

	@Override
	public Chat getChatById(Long chatId) throws Exception {
		Optional<Chat> chat=crepo.findById(chatId);
		if(chat.isEmpty()) throw new Exception("Chat not found");
		else return chat.get();
	}

}
