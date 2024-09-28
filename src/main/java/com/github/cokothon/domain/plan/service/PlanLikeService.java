package com.github.cokothon.domain.plan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.cokothon.domain.plan.dto.response.IsLikeResponse;
import com.github.cokothon.domain.plan.exception.PlanNotFoundException;
import com.github.cokothon.domain.plan.repository.PlanRepository;
import com.github.cokothon.domain.plan.schema.Plan;
import com.github.cokothon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanLikeService {

	private final PlanRepository planRepository;

	public IsLikeResponse isLikePlan(User user, String planId) {

		Plan plan = planRepository.findById(planId)
								  .orElseThrow(PlanNotFoundException::new);

		boolean isLike = plan.getLike()
							 .contains(user);

		return IsLikeResponse.builder()
							 .isLike(isLike)
							 .build();
	}

	public void likePlan(User user, String planId) {

		Plan plan = planRepository.findById(planId)
								  .orElseThrow(PlanNotFoundException::new);

		List<User> like = new ArrayList<>(plan.getLike());
		boolean isLike = like.contains(user);

		if (isLike) {
			return;
		}

		like.add(user);
		plan.setLike(like);

		planRepository.save(plan);
	}

	public void unlikePlan(User user, String planId) {

		Plan plan = planRepository.findById(planId)
								  .orElseThrow(PlanNotFoundException::new);

		List<User> like = new ArrayList<>(plan.getLike());
		boolean isLike = like.contains(user);

		if (!isLike) {

			return;
		}

		like.remove(user);
		plan.setLike(like);

		planRepository.save(plan);
	}
}
