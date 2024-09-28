package com.github.cokothon.domain.plan.dto.response;

import com.github.cokothon.domain.plan.schema.Plan;
import lombok.Builder;

@Builder
public record GetPlanResponse(
        Plan plan
) {
}
