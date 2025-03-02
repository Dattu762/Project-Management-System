package com.ProjectManagement.Application.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.ProjectManagement.Application.Model.Issue;
import com.ProjectManagement.Application.Model.Project;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Repo.IssueRepo;
import com.ProjectManagement.Application.Request.IssueRequest;

@Service
public class IssueServiceImpl implements IssueService {

	@Autowired
	IssueRepo irepo;
	@Autowired
	ProjectService pservice;
	@Autowired
	UserService uservice;
	
	@Override
	public Issue getIssueByIssueId(Long IssueId) throws Exception {
		
		Optional<Issue> issue=irepo.findById(IssueId);
		if(issue.isPresent()) return issue.get();
		else throw new Exception("Issue not found"+IssueId);
	}

	@Override
	public List<Issue> getIssuesByProjectId(Long projId) throws Exception {
//		Project project=pservice.findProjectById(projId);
		List<Issue> issues=irepo.findByProjectID(projId);
		return issues;
	}

	@Override
	public Issue createIssue(IssueRequest issue, User user) throws Exception {
		Issue createdissue= new Issue();
		
		createdissue.setIssueName(issue.getIssueName());
		createdissue.setDescription(issue.getDescription());
		createdissue.setStatus(issue.getStatus());
		createdissue.setProjectID(issue.getProjectID());
		createdissue.setPriority(issue.getPriority());
		createdissue.setDuedate(issue.getDuedate());
		
		createdissue.setProject(pservice.findProjectById(issue.getProjectID()));
		
		Issue Sissue=irepo.save(createdissue);
		return Sissue;
	}

	@Override
	public void deleteIssue(Long IssueId, Long userId) throws Exception {
		irepo.deleteById(IssueId);
	}

	@Override
	public Issue addUserToIssue(Long issueID, Long userId) throws Exception {
		 User user=uservice.findUserByid(userId);
		 Issue issue=getIssueByIssueId(issueID);
		 issue.setAssignee(user);
		 return irepo.save(issue);
	}

	@Override
	public Issue updateStatus(Long issueId, String status) throws Exception {
		Issue issue=getIssueByIssueId(issueId);
		issue.setStatus(status);
		return irepo.save(issue);
	}
	
	

}
