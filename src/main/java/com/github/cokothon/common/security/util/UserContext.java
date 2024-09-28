package com.github.cokothon.common.security.util;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.cokothon.common.security.authentication.UserAuthentication;
import com.github.cokothon.domain.user.schema.User;

public class UserContext {

	public static User getUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		UserAuthentication authentication = (UserAuthentication)context.getAuthentication();

		return authentication.getPrincipal();
	}
}
