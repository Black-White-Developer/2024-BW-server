package com.github.cokothon.domain.board.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import com.github.cokothon.common.email.EmailSender;
import com.github.cokothon.domain.auth.exception.NotPermitException;
import com.github.cokothon.domain.board.dto.request.CreateBoardRequest;
import com.github.cokothon.domain.board.dto.response.GetBoardResponse;
import com.github.cokothon.domain.board.dto.response.GetBoardsResponse;
import com.github.cokothon.domain.board.dto.response.IsMatchBoardResponse;
import com.github.cokothon.domain.board.email.MatchTemplate;
import com.github.cokothon.domain.board.exception.BoardNotFoundException;
import com.github.cokothon.domain.board.repository.BoardRepository;
import com.github.cokothon.domain.board.schema.Board;
import com.github.cokothon.domain.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MongoTemplate mongoTemplate;
    private final EmailSender emailSender;

    public void createBoard(User user, CreateBoardRequest dto) {

        String title = dto.title();
        String content = dto.content();

        Board board = Board.builder()
                .author(user)
                .title(title)
                .content(content)
                .like(List.of())
                .match(List.of())
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
        Aggregation aggregation = newAggregation(
            addFields().addField("likeCount")
                       .withValueOfExpression("size(like)")
                       .build(),

            sort(Sort.Direction.DESC, "likeCount"),

            limit(10)
        );

        List<Board> boards = mongoTemplate.aggregate(aggregation, "board", Board.class)
                .getMappedResults();

        return GetBoardsResponse.builder()
                .boards(boards)
                .build();
    }

    public GetBoardsResponse my(User user) {

        Query query = new Query()
                .addCriteria(Criteria.where("author").is(user));

        List<Board> boards = mongoTemplate.find(query, Board.class);

        return GetBoardsResponse.builder()
                .boards(boards)
                .build();
    }

    public GetBoardsResponse myLike(User user) {

        Query query = new Query()
                .addCriteria(Criteria.where("like").in(user));

        List<Board> boards = mongoTemplate.find(query, Board.class);

        return GetBoardsResponse.builder()
                .boards(boards)
                .build();
    }

    public IsMatchBoardResponse isMatchBoard(User user, String boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        return IsMatchBoardResponse.builder()
                .isMatch(board.getMatch().contains(user))
                .build();
    }

    public void matchBoard(User user, String boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        if (board.getAuthor().equals(user)) {
            throw new NotPermitException();
        }

        List<User> match = new ArrayList<>(board.getMatch());

        if (match.contains(user)) {
            return;
        }

        match.add(user);
        board.setMatch(match);

        emailSender.send(board.getAuthor().getEmail(), MatchTemplate.of(board, user));

        boardRepository.save(board);
    }
}
