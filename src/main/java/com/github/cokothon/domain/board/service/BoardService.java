package com.github.cokothon.domain.board.service;

import com.github.cokothon.domain.auth.exception.NotPermitException;
import com.github.cokothon.domain.board.dto.request.CreateBoardRequest;
import com.github.cokothon.domain.board.dto.response.GetBoardResponse;
import com.github.cokothon.domain.board.dto.response.GetBoardsResponse;
import com.github.cokothon.domain.board.exception.BoardNotFoundException;
import com.github.cokothon.domain.board.repository.BoardRepository;
import com.github.cokothon.domain.board.schema.Board;
import com.github.cokothon.domain.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.addFields;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MongoTemplate mongoTemplate;

    public void createBoard(User user, CreateBoardRequest dto) {

        String title = dto.title();
        String content = dto.content();

        Board board = Board.builder()
                .author(user)
                .title(title)
                .content(content)
                .like(List.of())
                .build();

        boardRepository.save(board);
    }

    public GetBoardResponse readBoard(String boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        return GetBoardResponse.builder()
                .board(board)
                .build();
    }

    public void modifyBoard(String boardId, User user, CreateBoardRequest dto) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        if(!board.getAuthor()
                .equals(user)) {

            throw new NotPermitException();
        }

        board.setTitle(dto.title());
        board.setContent(dto.content());

        boardRepository.save(board);
    }

    public void deleteBoard(String boardId, User user) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        if(!board.getAuthor()
                .equals(user)) {

            throw new NotPermitException();
        }

        boardRepository.delete(board);
    }

    public GetBoardsResponse getBoards() {
        List<Board> boards = boardRepository.findAll(Sort.by(Sort.Order.asc("createdAt")));

        return GetBoardsResponse
                .builder()
                .boards(boards)
                .build();
    }

    public GetBoardsResponse getBestBoards() {

        Aggregation aggregation = Aggregation.newAggregation(
                addFields().addField("likeCount").withValueOfToArraySize("like").build(),


        )

        return GetBoardsResponse.builder()
                .boards(boards)
                .build();
    }
}
