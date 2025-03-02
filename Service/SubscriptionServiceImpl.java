package com.ProjectManagement.Application.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProjectManagement.Application.Model.PlanType;
import com.ProjectManagement.Application.Model.Subscription;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Repo.SubscriptionRepo;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private SubscriptionRepo srepo;
	
	
	@Override
	public Subscription createSubscription(User user) throws Exception {
		Subscription subscription=new Subscription();
		
		subscription.setUser(user);
		subscription.setPlanType(PlanType.Free);
		subscription.setStartDate(LocalDate.now());
		subscription.setEndDate(LocalDate.now().plusMonths(12));
		subscription.setValid(true);
		
		return srepo.save(subscription);
	}

	@Override
	public Subscription getSubscription(Long userId) throws Exception {
		Subscription sub=srepo.findByUserId(userId);
		
		if(!isValid(sub)) {
			sub.setPlanType(PlanType.Free);
			sub.setStartDate(LocalDate.now());
			sub.setEndDate(LocalDate.now().plusMonths(12));
		}
		return srepo.save(sub);
	}

	@Override
	public Subscription upgradeSubscription(Long userId, PlanType planType) throws Exception {
		Subscription sub=srepo.findByUserId(userId);
		sub.setPlanType(planType);
		sub.setStartDate(LocalDate.now());
		if(planType.equals(PlanType.Monthly)) sub.setEndDate(LocalDate.now().plusMonths(1));
		else if(planType.equals(PlanType.Annually)) sub.setEndDate(LocalDate.now().plusMonths(12));
		
		return srepo.save(sub);
	}

	@Override
	public boolean isValid(Subscription subscription) throws Exception {
		if(subscription.getPlanType().equals(PlanType.Free)) return true;
		
		return subscription.getEndDate().isAfter(LocalDate.now()) || subscription.getEndDate().equals(LocalDate.now());
	}

}
