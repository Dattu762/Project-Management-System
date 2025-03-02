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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ProjectManagement.Application.Model.Issue;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Request.IssueRequest;
import com.ProjectManagement.Application.Response.MessageResponse;
import com.ProjectManagement.Application.Service.IssueService;
import com.ProjectManagement.Application.Service.UserService;

import DTO.IssueDTO;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("api/issues")
public class IssueController {

	@Autowired
	IssueService iservice;
	@Autowired
	UserService uservice;
	
	@GetMapping("project/{projid}")
	public ResponseEntity<List<Issue>> getissues(
			@PathVariable Long projid) throws Exception{
		
		//User user=uservice.findUserProfileByJwt(jwt);
		List<Issue> issues=iservice.getIssuesByProjectId(projid);
	
		return new ResponseEntity<>(issues,HttpStatus.OK);
	}
	
	@GetMapping("/{issueId}")
	public ResponseEntity<Issue> getIssue(
			@PathVariable Long issueId) throws Exception{
		
		//User user=uservice.findUserProfileByJwt(jwt);
		Issue issue=iservice.getIssueByIssueId(issueId);
		
		return new ResponseEntity<>(issue,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<IssueDTO> addIssue(
			@RequestBody IssueRequest issue,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User tokenuser=uservice.findUserProfileByJwt(jwt);
		Issue Sissue=iservice.createIssue(issue, tokenuser);
		
		IssueDTO issuedto=new IssueDTO();
		issuedto.setId(Sissue.getId());
		issuedto.setIssueName(Sissue.getIssueName());
		issuedto.setPriority(Sissue.getPriority());
		issuedto.setProject(Sissue.getProject());
		issuedto.setProjectID(Sissue.getProjectID());
		issuedto.setStatus(Sissue.getStatus());
		issuedto.setTags(Sissue.getTags());
		issuedto.setDescription(Sissue.getDescription());
		issuedto.setAssignee(Sissue.getAssignee());
		issuedto.setDuedate(Sissue.getDuedate());
		
		return new ResponseEntity<>(issuedto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{issueId}")
	public ResponseEntity<MessageResponse> deleteIssue(
			@PathVariable Long issueId,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=uservice.findUserProfileByJwt(jwt);
		iservice.deleteIssue(issueId, user.getId());
		MessageResponse mes=new MessageResponse("Issue Created Successfully");
		
		return new ResponseEntity<>(mes,HttpStatus.OK);
	}
	
	@PutMapping("/{userId}/adduser/{issueId}")
	public ResponseEntity<Issue> addUserTOIssue(
			@PathVariable Long issueId,
			@PathVariable Long userId) throws Exception{
		
//		User user=uservice.findUserProfileByJwt(jwt);
		Issue issue=iservice.addUserToIssue(issueId, userId);
		
		return new ResponseEntity<>(issue,HttpStatus.OK);
	}
	
	@PutMapping("/{issueId}/status/{status}")
	public ResponseEntity<Issue> updateStatus(
			@PathVariable String status,
			@PathVariable Long issueId
			) throws Exception{
		
//		User user=uservice.findUserProfileByJwt(jwt);
		Issue Sstatus=iservice.updateStatus(issueId, status);
		return new ResponseEntity<>(Sstatus,HttpStatus.OK);
	}
	
	
	
	
	
	
}
