package com.ProjectManagement.Application.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProjectManagement.Application.Model.Issue;

public interface IssueRepo extends JpaRepository<Issue, Long> {
	
	public List<Issue> findByProjectID(Long projectID);
}
