package com.ProjectManagement.Application.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String fullname;
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY) // Not to send the password to the frontend
	private String password;
	
	@JsonIgnore
	@OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
	private List<Issue> issues=new ArrayList<Issue>();
	
	private int projectSize;
	
	@JsonIgnore
	@OneToMany(mappedBy = "projectLead", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Project> ProjectsLeadBy;
	
	@ManyToMany
	private List<Project> projects;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments=new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "Sender",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Message> messages=new ArrayList<>();

	@ManyToMany
	private List<Chat> chats=new ArrayList<>();
	
	@OneToOne
	private Subscription subscription;
	
//	@JsonIgnore
//	@OneToMany(mappedBy = "User",cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Invitation> InvitationsOwnedBy=new ArrayList<>();
//	
//	@ManyToMany
//	private List<Invitation> InvitationsReceived=new ArrayList<>();
	
}
