package com.github.cokothon.domain.plan.schema;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.github.cokothon.common.schema.BaseSchema;
import com.github.cokothon.domain.user.schema.User;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Plan extends BaseSchema {

	private String title;

	private String content;

	@DBRef
	private User author;

	@DBRef
	private List<User> like;
}

