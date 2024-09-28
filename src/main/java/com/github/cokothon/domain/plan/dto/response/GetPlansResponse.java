package com.github.cokothon.domain.plan.dto.response;

import java.util.List;

import com.github.cokothon.domain.plan.schema.Plan;

import lombok.Builder;

@Builder
public record GetPlansResponse(

	List<Plan> plans
) {
}
