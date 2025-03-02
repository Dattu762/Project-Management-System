package com.ProjectManagement.Application.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ProjectManagement.Application.Model.Project;
import com.ProjectManagement.Application.Model.User;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long>{
	
//	List<Project> findByProjectLead(User projectLead);
	
	List<Project> findByPnameContainingAndTeamContains(String serachName, User user);//For searching the projects using search.
	
	List<Project> findByTeamContainingOrProjectLead(User user, User projectLead);// Getting all the projectsof user as team member and as projectLead.
	
//	@Query("select p from Project p join p.team t where t=:user")
//	List<Project> findProjectByTeam(@Param("user") User user); // To get the all projects of particular user.		
	
}
