package com.ProjectManagement.Application.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProjectManagement.Application.Model.Chat;

public interface ChatRepo extends JpaRepository<Chat, Long> {

}
