package com.ProjectManagement.Application.Service;

import java.util.List;

import com.ProjectManagement.Application.Model.Message;

public interface MessageService {

	List<Message> getMessagesByProjectId(Long projectId) throws Exception;
	
	Message sendMessage(String content, Long userId, Long projectId) throws Exception;
	
	void deleteMessage(Long userId, Long messageId) throws Exception;
}
