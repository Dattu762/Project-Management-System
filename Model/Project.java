package com.ProjectManagement.Application.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String pname;
	private String description;
	private String category;
	//private String status;
	
	private List<String> tags=new ArrayList<>();
	
	@ManyToOne
	private User projectLead;
	
	@JsonIgnore
	@OneToOne(mappedBy = "project",cascade = CascadeType.ALL,orphanRemoval = true)
	private Chat chat;
	
	@JsonIgnore
	@OneToMany(mappedBy = "project",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Issue> issues=new ArrayList<>();
	
	@ManyToMany
	private List<User> team=new ArrayList<>();


}
