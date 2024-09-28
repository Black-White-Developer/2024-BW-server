package com.github.cokothon.domain.comment.service;

import com.github.cokothon.common.schema.BaseSchema;
import com.github.cokothon.domain.auth.exception.NotPermitException;
import com.github.cokothon.domain.board.exception.BoardNotFoundException;
import com.github.cokothon.domain.board.repository.BoardRepository;
import com.github.cokothon.domain.comment.dto.request.CreateCommentRequest;
import com.github.cokothon.domain.comment.dto.response.GetCommentsResponse;
import com.github.cokothon.domain.comment.exception.CommentNotFoundException;
import com.github.cokothon.domain.comment.repository.CommentRepository;
import com.github.cokothon.domain.comment.schema.Comment;
import com.github.cokothon.domain.plan.exception.PlanNotFoundException;
import com.github.cokothon.domain.plan.repository.PlanRepository;
import com.github.cokothon.domain.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PlanRepository planRepository;
    private final BoardRepository boardRepository;
    private final MongoTemplate mongoTemplate;

    public void createComment(String parent, String parentId, CreateCommentRequest dto, User user) {

        BaseSchema schema;
        String type;

        if(parent.equalsIgnoreCase("plan")) {
            schema = planRepository.findById(parentId)
                    .orElseThrow(PlanNotFoundException::new);
            type = "plan";
        } else {
            schema = boardRepository.findById(parentId)
                    .orElseThrow(BoardNotFoundException::new);
            type = "board";
        }

        Comment comment = Comment.builder()
                .parent(schema.getId())
                .author(user)
                .type(type)
                .content(dto.content())
                .build();

        commentRepository.save(comment);
    }

    public void modifyComment(User user, String commentId, CreateCommentRequest dto) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if(!comment.getAuthor()
                .equals(user)) {

            throw new NotPermitException();
        }

        comment.setContent(dto.content());

        commentRepository.save(comment);
    }

    public void deleteComment(User user, String commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if(!comment.getAuthor()
                .equals(user)) {

            throw new NotPermitException();
        }

        commentRepository.delete(comment);
    }

    public GetCommentsResponse getComments(String parent, String parentId) {

        BaseSchema schema;

        if(parent.equalsIgnoreCase("plan")) {
            schema = planRepository.findById(parentId)
                    .orElseThrow(PlanNotFoundException::new);
        } else {
            schema = boardRepository.findById(parentId)
                    .orElseThrow(BoardNotFoundException::new);
        }

        String id = schema.getId();

        Query query = new Query()
                .addCriteria(Criteria.where("parent").is(id));

        List<Comment> comments = mongoTemplate.find(query, Comment.class);

        return GetCommentsResponse.builder()
                .comments(comments)
                .build();
    }
}
