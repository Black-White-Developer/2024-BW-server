package com.github.cokothon.domain.plan.controller;

import com.github.cokothon.domain.plan.dto.response.GetPlanResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cokothon.common.api.dto.response.ApiResponse;
import com.github.cokothon.common.security.util.UserContext;
import com.github.cokothon.domain.plan.dto.request.CreatePlanRequest;
import com.github.cokothon.domain.plan.dto.response.GetPlansResponse;
import com.github.cokothon.domain.plan.service.PlanService;
import com.github.cokothon.domain.user.schema.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanController {

	private final PlanService planService;

	@GetMapping
	public ApiResponse<GetPlansResponse> getPlans() {

		return ApiResponse.ok(planService.getPlans());
	}

	@GetMapping("/{planId}")
	public ApiResponse<GetPlanResponse> getPlan(@PathVariable("planId") String planId) {


		return ApiResponse.ok(planService.getPlan(planId));
	}

	@GetMapping("/best")
	public ApiResponse<GetPlansResponse> getBestPlans() {

		return ApiResponse.ok(planService.getBestPlans());
	}

	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<Void> createPlan(@RequestBody @Valid CreatePlanRequest request) {

		User user = UserContext.getUser();

		planService.createPlan(user, request);

		return ApiResponse.ok();
	}

	@DeleteMapping("/{planId}")
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<Void> deletePlan(@PathVariable("planId") String planId) {

		User user = UserContext.getUser();

		planService.deletePlan(user, planId);

		return ApiResponse.ok();
	}

	@GetMapping("/my")
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<GetPlansResponse> my() {

		User user = UserContext.getUser();

		return ApiResponse.ok(planService.my(user));
	}

	@GetMapping("/myLike")
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<GetPlansResponse> myLike() {

		User user = UserContext.getUser();

		return ApiResponse.ok(planService.myLike(user));
	}
}
