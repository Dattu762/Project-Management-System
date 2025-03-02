package DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ProjectManagement.Application.Model.Project;
import com.ProjectManagement.Application.Model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
	
	private Long id;
	private String issueName;
	private String description;
	private String status;
	private Long projectID;
	private String priority;
	private LocalDate duedate;
	private List<String> tags=new ArrayList<>();
	
	private Project project;
	private User Assignee;
	
}
