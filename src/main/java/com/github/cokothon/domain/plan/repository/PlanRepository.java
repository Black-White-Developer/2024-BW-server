package com.github.cokothon.domain.plan.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.cokothon.domain.plan.schema.Plan;

public interface PlanRepository extends MongoRepository<Plan, String> {
}
