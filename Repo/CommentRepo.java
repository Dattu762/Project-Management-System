package com.ProjectManagement.Application.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProjectManagement.Application.Model.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

	List<Comment> findByIssueId(Long issueId);
}
