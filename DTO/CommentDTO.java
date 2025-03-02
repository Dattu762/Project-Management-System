package DTO;

import java.security.PrivateKey;

import com.ProjectManagement.Application.Model.Issue;
import com.ProjectManagement.Application.Model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

	private String content;
	private User user;
	private Issue issue;
}
