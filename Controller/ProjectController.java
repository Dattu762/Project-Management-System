package com.ProjectManagement.Application.Controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.ProjectManagement.Application.Model.Chat;
import com.ProjectManagement.Application.Model.Invitation;
import com.ProjectManagement.Application.Model.Message;
import com.ProjectManagement.Application.Model.Project;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Request.InviteRequest;
import com.ProjectManagement.Application.Response.MessageResponse;
import com.ProjectManagement.Application.Service.InvitationService;
import com.ProjectManagement.Application.Service.ProjectService;
import com.ProjectManagement.Application.Service.UserService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("api/projects")
public class ProjectController {
	
	@Autowired
	ProjectService pservice;
	@Autowired
	UserService uservice;
	@Autowired
	InvitationService iservice;
	
	@GetMapping
    public ResponseEntity<List<Project>> getprojects(@RequestParam(required = false) String category, 
    		@RequestParam(required = false) String tag,
    		@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=uservice.findUserProfileByJwt(jwt);
		List<Project> projects =pservice.getProjectByTeam(user, category, tag);
		return new ResponseEntity<>(projects , HttpStatus.OK);
	}
	
	@GetMapping("/{projectid}")
	public ResponseEntity<Project> getprojectbyid( @PathVariable Long projectid,
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user=uservice.findUserProfileByJwt(jwt);
		Project project=pservice.findProjectById(projectid);
		
		return new ResponseEntity<>(project,HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Project>> getProjectsOnSearch(
			@RequestParam(required = false) String searchtext,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=uservice.findUserProfileByJwt(jwt);
		List<Project> projects=pservice.searchProjects(searchtext, user);
		return new ResponseEntity<>(projects,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Project> createProject(@RequestBody Project project,
			@RequestHeader("Authorization")String jwt) throws Exception{
		User user=uservice.findUserProfileByJwt(jwt);
		Project createdproject=pservice.CreateProject(project,user);
		
		return new ResponseEntity<>(createdproject,HttpStatus.OK);
	}
	
	@PutMapping("/{projectid}")
	public ResponseEntity<Project> updateProject(
			@PathVariable Long projectid,
			@RequestBody Project project,
			@RequestHeader("Authorization")String jwt) throws Exception{
		User user=uservice.findUserProfileByJwt(jwt);
		Project updatedproject=pservice.UpdateProject(project,projectid);
		
		return new ResponseEntity<>(updatedproject,HttpStatus.OK);
	}
	
	@DeleteMapping("/{projectid}")
	public ResponseEntity<MessageResponse> deleteProject(
			@PathVariable Long projectid,
			@RequestHeader("Authorization")String jwt) throws Exception{
		User user=uservice.findUserProfileByJwt(jwt);
		pservice.DeleteProject(projectid,user.getId());
		MessageResponse mres=new MessageResponse("Project Deleted Successfully!!");
		
		return new ResponseEntity<>(mres,HttpStatus.OK);
	}
	
	@GetMapping("/{projectid}/chat")
	public ResponseEntity<Chat> getChatByProject( 
			@PathVariable Long projectid,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=uservice.findUserProfileByJwt(jwt);
		Chat chat=pservice.getChat(projectid);
		
		return new ResponseEntity<>(chat,HttpStatus.OK);
	}
	
	@PostMapping("/invite")
	public ResponseEntity<MessageResponse> inviteToProject(
			@RequestBody InviteRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=uservice.findUserProfileByJwt(jwt);
		iservice.sendInvitation(req.getEmail(), req.getProjectId());
		MessageResponse mes=new MessageResponse("Invitation Sent");
		return new ResponseEntity<>(mes,HttpStatus.OK);
	}
	
	@GetMapping("/accept_invitation")
	public ResponseEntity<Invitation> acceptInviteToProject(
			@RequestParam String token,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=uservice.findUserProfileByJwt(jwt);
		Invitation invitation=iservice.accceptInvitation(token, user.getId());
		pservice.addUserToProject(invitation.getProjectId(), user.getId());
		
		return new ResponseEntity<>(invitation,HttpStatus.OK);
	}
	
	
	
	
	
}
