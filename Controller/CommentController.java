package com.ProjectManagement.Application.Controller;

import java.net.http.HttpResponse;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.ProjectManagement.Application.Model.Comment;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Request.CommentRequest;
import com.ProjectManagement.Application.Response.MessageResponse;
import com.ProjectManagement.Application.Service.CommentService;
import com.ProjectManagement.Application.Service.UserService;

import DTO.CommentDTO;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	CommentService cservice;
	@Autowired
	UserService uservice;
	
	@PostMapping
	public ResponseEntity<CommentDTO> createComment(
			@RequestBody CommentRequest creq,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=uservice.findUserProfileByJwt(jwt);
		Comment comment=cservice.createComment(creq.getContent(),user.getId(),creq.getIssueId());
		
		CommentDTO dcomment= new CommentDTO();
		dcomment.setContent(comment.getContent());
		dcomment.setUser(comment.getUser());
		dcomment.setIssue(comment.getIssue());
		
		return new ResponseEntity<>(dcomment,HttpStatus.OK);
	}
	
	@GetMapping("/{issueId}")
	public ResponseEntity<List<Comment>> getAllComments(
			@PathVariable Long issueId) throws Exception{
		
		List<Comment> comments=cservice.getComments(issueId);
		
		return new ResponseEntity<>(comments,HttpStatus.OK);
	}
	
	@DeleteMapping("/{commentid}")
	public ResponseEntity<MessageResponse> deleteComment(
			@PathVariable Long commentid,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=uservice.findUserProfileByJwt(jwt);
	    cservice.deleteComment(commentid, user.getId());
	    MessageResponse mes=new MessageResponse("Comment deleted successfully");
		
		return new ResponseEntity<>(mes,HttpStatus.OK);
	}
	
	@PutMapping("/{content}/update/{commentId}")
	public ResponseEntity<String> updateComment(
			@PathVariable String content,
			@PathVariable Long commentId,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=uservice.findUserProfileByJwt(jwt);
	    cservice.updateComment(content, commentId, user.getId());
		
	    return new ResponseEntity<>(content,HttpStatus.OK);
	}
	
	
	
}
