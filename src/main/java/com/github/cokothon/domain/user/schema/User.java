package com.github.cokothon.domain.user.schema;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
	private String username;

	@JsonIgnore
	private String password;

	private Role role;

	public enum Role {

		MEMBER,
		ADMIN(MEMBER),
		;

		private final Role[] inheritedRoles;

		Role(Role... inheritedRoles) {
			this.inheritedRoles = inheritedRoles;
		}

		public Collection<SimpleGrantedAuthority> getAuthorization() {

			Set<Role> authorization = new HashSet<>();

			collectAuthorization(this, authorization);

			return authorization.stream().map(role -> "ROLE_" + role.name()).map(SimpleGrantedAuthority::new).toList();
		}

		private void collectAuthorization(Role role, Set<Role> roles) {

			roles.add(role);

			for (Role inheritedRole : role.inheritedRoles) {
				collectAuthorization(inheritedRole, roles);
			}
		}
	}
}

