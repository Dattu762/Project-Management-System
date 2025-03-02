package com.ProjectManagement.Application.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ProjectManagement.Application.Model.Comment;
import com.ProjectManagement.Application.Repo.CommentRepo;

public interface CommentService {
	
	List<Comment> getComments(Long Issueid) throws Exception;
	
//	Comment getComment(Long commentId);
	
	Comment createComment(String commentcont, Long UserId, Long IssueId) throws Exception;
	
	void deleteComment(Long commentId, Long UserId) throws Exception;
	
	String updateComment(String content, Long CommentId,Long userId) throws Exception;
	
	
}
