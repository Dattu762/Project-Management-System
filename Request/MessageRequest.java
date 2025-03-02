package com.ProjectManagement.Application.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

	private String content;
	private Long projectId;
	private Long senderId;
}
