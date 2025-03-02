package com.ProjectManagement.Application.Request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequest {

	private String issueName;
	private String description;
	private String status;
	private Long projectID;
	private String priority;
	private LocalDate duedate;

}
