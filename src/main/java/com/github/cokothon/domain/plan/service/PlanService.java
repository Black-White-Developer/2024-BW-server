package com.github.cokothon.domain.plan.service;

import java.util.List;

import com.github.cokothon.domain.plan.dto.response.GetPlanResponse;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.github.cokothon.domain.auth.exception.NotPermitException;
import com.github.cokothon.domain.plan.dto.request.CreatePlanRequest;
import com.github.cokothon.domain.plan.dto.response.GetPlansResponse;
import com.github.cokothon.domain.plan.exception.PlanNotFoundException;
import com.github.cokothon.domain.plan.repository.PlanRepository;
import com.github.cokothon.domain.plan.schema.Plan;
import com.github.cokothon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanService {

	private final PlanRepository planRepository;
	private final MongoTemplate mongoTemplate;

	public GetPlansResponse getPlans() {

		List<Plan> plans = planRepository.findAll(Sort.by(Sort.Order.asc("createdAt")));

		return GetPlansResponse.builder()
							   .plans(plans)
							   .build();
	}

	public GetPlanResponse getPlan(String planId) {

		Plan plan = planRepository.findById(planId)
				.orElseThrow(PlanNotFoundException::new);

		return GetPlanResponse.builder()
				.plan(plan)
				.build();
	}

	public GetPlansResponse getBestPlans() {

		Query query = new Query()
			.with(Sort.by(
				Sort.Order.desc("like.size"),
				Sort.Order.asc("createdAt")
			))
			.limit(10);

		List<Plan> plans = mongoTemplate.find(query, Plan.class);

		return GetPlansResponse.builder()
							   .plans(plans)
							   .build();
	}

	public void createPlan(User user, CreatePlanRequest dto) {

		String title = dto.title();
		String content = dto.content();

		Plan plan = Plan.builder()
						.title(title)
						.content(content)
						.author(user)
						.like(List.of())
						.build();

		planRepository.save(plan);
	}

	public void deletePlan(User user, String planId) {

		Plan plan = planRepository.findById(planId)
								  .orElseThrow(PlanNotFoundException::new);

		if (!plan.getAuthor()
				 .equals(user)) {

			throw new NotPermitException();
		}

		planRepository.delete(plan);
	}
}
