package com.ProjectManagement.Application.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProjectManagement.Application.Model.Subscription;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {

	Subscription findByUserId(Long userId);
}
