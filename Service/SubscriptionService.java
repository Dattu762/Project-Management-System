package com.ProjectManagement.Application.Service;

import com.ProjectManagement.Application.Model.PlanType;
import com.ProjectManagement.Application.Model.Subscription;
import com.ProjectManagement.Application.Model.User;

public interface SubscriptionService {
	
	Subscription createSubscription(User user) throws Exception;
	
	Subscription getSubscription(Long userId)throws Exception;
	
	Subscription upgradeSubscription(Long userId, PlanType planType)throws Exception;
	
	boolean isValid(Subscription subscription)throws Exception;

}
