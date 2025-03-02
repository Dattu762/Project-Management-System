package com.ProjectManagement.Application.Service;



import java.util.List;

import com.ProjectManagement.Application.Model.Chat;
import com.ProjectManagement.Application.Model.Project;
import com.ProjectManagement.Application.Model.User;

public interface ProjectService {
	
	Project CreateProject(Project project, User user) throws Exception;
	
	List<Project> getProjectByTeam(User user, String category, String tag) throws Exception;
	
	Project findProjectById(Long projectId) throws Exception;
	
	Project UpdateProject(Project updatedProject, Long projectId) throws Exception;
	
	void DeleteProject(Long projectId, Long userId) throws Exception;
	
	void addUserToProject(Long projectId, Long userId) throws Exception;
	
	void removeUserFromProject(Long projectId, Long userId) throws Exception;
	
	Chat getChat(Long projectId) throws Exception;
	
	List<Project> searchProjects(String searchName, User user) throws Exception;

}
