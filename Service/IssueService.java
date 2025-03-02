package com.ProjectManagement.Application.Service;

import java.util.List;
import java.util.Optional;

import com.ProjectManagement.Application.Model.Issue;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Request.IssueRequest;


public interface IssueService {

	Issue getIssueByIssueId(Long IssueId) throws Exception;
	
	List<Issue> getIssuesByProjectId(Long projId) throws Exception;
	
	Issue createIssue(IssueRequest issue, User user) throws Exception;
	
	void deleteIssue(Long IssueId, Long userId) throws Exception;
	
	Issue addUserToIssue(Long issueID, Long userId) throws Exception;
	
	Issue updateStatus(Long issueId, String status) throws Exception;
}
