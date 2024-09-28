package com.github.cokothon.domain.comment.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.cokothon.domain.comment.schema.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

	List<Comment> findAllByParent(String parent);
}
