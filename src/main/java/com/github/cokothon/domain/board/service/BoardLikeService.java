package com.github.cokothon.domain.board.service;


import com.github.cokothon.domain.board.dto.response.IsLikeResponse;
import com.github.cokothon.domain.board.exception.BoardNotFoundException;
import com.github.cokothon.domain.board.repository.BoardRepository;
import com.github.cokothon.domain.board.schema.Board;
import com.github.cokothon.domain.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardRepository boardRepository;

    public IsLikeResponse isLikeBoard(User user, String boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        boolean isLike = board.getLike()
                .contains(user);

        return IsLikeResponse.builder()
                .isLike(isLike)
                .build();
    }

    public void likeBoard(User user, String boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        List<User> like = new ArrayList<>(board.getLike());
        boolean isLike = like.contains(user);

        if(isLike) {
            return;
        }

        like.add(user);
        board.setLike(like);

        boardRepository.save(board);
    }

    public void unlikeBoard(User user, String boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        List<User> like = new ArrayList<>(board.getLike());
        boolean isLike = like.contains(user);

        if(!isLike) {
            return;
        }

        like.remove(user);
        board.setLike(like);

        boardRepository.save(board);
    }
}
