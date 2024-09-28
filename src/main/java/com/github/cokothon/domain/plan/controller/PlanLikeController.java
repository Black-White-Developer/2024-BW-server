package com.github.cokothon.domain.plan.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cokothon.common.api.dto.response.ApiResponse;
import com.github.cokothon.common.security.util.UserContext;
import com.github.cokothon.domain.plan.dto.response.IsLikeResponse;
import com.github.cokothon.domain.plan.service.PlanLikeService;
import com.github.cokothon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/plan/like")
@RequiredArgsConstructor
public class PlanLikeController {

	private final PlanLikeService planLikeService;

	@GetMapping("/{planId}")
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<IsLikeResponse> isLikePlan(@PathVariable("planId") String planId) {

		User user = UserContext.getUser();

		return ApiResponse.ok(planLikeService.isLikePlan(user, planId));
	}

	@PutMapping("/{planId}")
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<Void> likePlan(@PathVariable("planId") String planId) {

		User user = UserContext.getUser();

		planLikeService.likePlan(user, planId);

		return ApiResponse.ok();
	}

	@DeleteMapping("/{planId}")
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<Void> unlikePlan(@PathVariable("planId") String planId) {

		User user = UserContext.getUser();

		planLikeService.unlikePlan(user, planId);

		return ApiResponse.ok();
	}
}
