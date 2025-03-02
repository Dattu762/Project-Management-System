package com.ProjectManagement.Application.Service;

import com.ProjectManagement.Application.Model.Chat;

public interface ChatService {
	
	Chat createChat(Chat chat);
	
	Chat getChatById(Long chatId) throws Exception;
}
