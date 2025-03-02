package com.ProjectManagement.Application.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProjectManagement.Application.Model.Message;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {

	List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId);
	
}
