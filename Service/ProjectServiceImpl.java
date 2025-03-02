package com.ProjectManagement.Application.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.handler.ExceptionHandlingWebHandler;

import com.ProjectManagement.Application.Model.Chat;
import com.ProjectManagement.Application.Model.Project;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Repo.ProjectRepo;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	ProjectRepo prepo;
	@Autowired
	UserService uservice;
	@Autowired
	ChatService cservice;
	
	
	@Override
	public Project CreateProject(Project project, User user) throws Exception {
		Project curProject=new Project();
		
		curProject.setPname(project.getPname());
		curProject.setDescription(project.getDescription());
		curProject.setCategory(project.getCategory());
		curProject.setProjectLead(user);
		curProject.setTags(project.getTags());
		curProject.getTeam().add(user);
		
		
		Project savedProject=prepo.save(curProject);
		
		
		Chat chat=new Chat();
		chat.setProject(savedProject);
		Chat savedChat=cservice.createChat(chat);
		
		savedProject.setChat(savedChat);
		return savedProject;
	}

	@Override
	public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
		List<Project> projects=prepo.findByTeamContainingOrProjectLead(user, user);
		
		if(category!=null) 
			projects=projects.stream().filter(project->project.getCategory().equals(category)).collect(Collectors.toList());
		if(tag!=null)
			projects=projects.stream().filter(project->project.getTags().contains(tag)).collect(Collectors.toList());
		
		return projects;
	}

	@Override
	public Project findProjectById(Long projectId) throws Exception {
		 Optional<Project> project=prepo.findById(projectId);
		return project.get();
	}

	@Override
	public Project UpdateProject(Project updatedProject, Long projectId) throws Exception {
		Project project=findProjectById(projectId);
		project.setPname(updatedProject.getPname());
		project.setCategory(updatedProject.getCategory());
		project.setTags(updatedProject.getTags());
		project.setDescription(updatedProject.getDescription());
		
		return prepo.save(project);
	}

	@Override
	public void DeleteProject(Long projectId, Long userId) throws Exception {
		
		prepo.deleteById(projectId);
		
	}

	@Override
	public void addUserToProject(Long projectId, Long userId) throws Exception {
		Project project=findProjectById(projectId);
		User user=uservice.findUserByid(userId);
		
		if(!project.getTeam().contains(user)) {
			project.getTeam().add(user);
			project.getChat().getUsers().add(user);
		}
		
		prepo.save(project);
	
	}

	@Override
	public void removeUserFromProject(Long projectId, Long userId) throws Exception {
		Project project=findProjectById(projectId);
		User user=uservice.findUserByid(userId);
		
		if(project.getTeam().contains(user)) {
			project.getTeam().remove(user);
			project.getChat().getUsers().remove(user);
		}
		prepo.save(project);
	}

	@Override
	public Chat getChat(Long projectId) throws Exception {
		Optional<Project> curProject=prepo.findById(projectId);
		
		return curProject.get().getChat();
	}

	@Override
	public List<Project> searchProjects(String searchName, User user) {
		List<Project> projects=prepo.findByPnameContainingAndTeamContains(searchName, user);
		return projects;
	}

}
