package com.github.cokothon.domain.board.schema;

import com.github.cokothon.common.schema.BaseSchema;
import com.github.cokothon.domain.user.schema.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Board extends BaseSchema {

    private String title;

    private String content;

    @DBRef
    private User author;

    @DBRef
    private List<User> like;

    @DBRef
    private List<User> match;
}