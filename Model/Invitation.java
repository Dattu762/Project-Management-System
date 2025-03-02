package com.ProjectManagement.Application.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invitation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String token;
	private String email;
	private Long projectId;
	
//	@ManyToOne
//	private User Sender;
//	
//	@ManyToMany
//	private List<User> receivers=new ArrayList<>();

}
