package com.ProjectManagement.Application.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProjectManagement.Application.Model.Comment;
import com.ProjectManagement.Application.Model.Issue;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Repo.CommentRepo;

@Service
public class CommentServiceImpl implements CommentService {
 
	@Autowired
	CommentRepo crepo;
	@Autowired
	IssueService iservice;
	@Autowired
	UserService uservice;
	
	@Override
	public List<Comment> getComments(Long Issueid) throws Exception {

//		Issue issue=iservice.getIssueByIssueId(Issueid);
//		List<Comment> comments=issue.getComments();
		return crepo.findByIssueId(Issueid);
	}

	@Override
	public Comment createComment(String commentcont, Long UserId, Long IssueId) throws Exception {
		Issue issue=iservice.getIssueByIssueId(IssueId);
		
		Comment comment=new Comment();
		comment.setContent(commentcont);
		comment.setIssue(issue);
		comment.setUser(uservice.findUserByid(UserId));
		comment.setCreatedDateTime(null);
		
		Comment Scomment=crepo.save(comment);
		issue.getComments().add(Scomment);
		
		return Scomment;
	}

	@Override
	public void deleteComment(Long commentId, Long UserId) throws Exception {
		
		Optional<Comment> comment=crepo.findById(commentId);
		if(comment.isEmpty()) throw new Exception("Comment not Found"+comment);
		
		if(comment.get().getUser().getId()==UserId) crepo.delete(comment.get());
		else throw new Exception("User does not have the permission to delete comment");
	}

	@Override
	public String updateComment(String content, Long CommentId, Long userId) throws Exception {
		Optional<Comment> comment=crepo.findById(CommentId);
		if(!comment.isEmpty() && comment.get().getUser().getId()==userId) comment.get().setContent(content);
		return content;
	}

}
