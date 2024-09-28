package com.github.cokothon.domain.user.schema;

import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.cokothon.common.schema.BaseSchema;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class User extends BaseSchema {

	@Indexed(unique = true)
	private String email;

	@JsonIgnore
	private String password;

	private String nickname;

	private int level;
}

