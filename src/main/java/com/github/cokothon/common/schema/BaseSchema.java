package com.github.cokothon.common.schema;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
public abstract class BaseSchema {

	@Id
	private String id;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		BaseSchema that = (BaseSchema)obj;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
