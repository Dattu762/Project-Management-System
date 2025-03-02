package com.ProjectManagement.Application.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ProjectManagement.Application.Model.PlanType;
import com.ProjectManagement.Application.Model.Subscription;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Service.SubscriptionService;
import com.ProjectManagement.Application.Service.UserService;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

	@Autowired
	SubscriptionService subService;
	@Autowired
	UserService uservice;
	
	@GetMapping("/user")
	public ResponseEntity<Subscription> getSubscription(
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=uservice.findUserProfileByJwt(jwt);
		Subscription sub=subService.getSubscription(user.getId());
		return new ResponseEntity<>(sub,HttpStatus.OK);
	}
	
	@PutMapping("/upgrade")
	public ResponseEntity<Subscription> upgradeSubscription(
			@RequestHeader("Authorization") String jwt,
			@RequestParam PlanType plantype) throws Exception{
		
		User user=uservice.findUserProfileByJwt(jwt);
		Subscription sub=subService.upgradeSubscription(user.getId(), plantype);
		
		return new ResponseEntity<>(sub,HttpStatus.ACCEPTED);
	}
	
	
}
