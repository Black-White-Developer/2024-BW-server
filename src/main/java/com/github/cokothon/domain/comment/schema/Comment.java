package com.github.cokothon.domain.comment.schema;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.github.cokothon.common.schema.BaseSchema;
import com.github.cokothon.domain.user.schema.User;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseSchema {

	private String parent;

	private String content;

	@DBRef
	private User author;
}

