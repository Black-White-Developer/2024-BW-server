package com.github.cokothon.domain.board.repository;

import com.github.cokothon.domain.board.schema.Board;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends MongoRepository<Board, String> {
}
