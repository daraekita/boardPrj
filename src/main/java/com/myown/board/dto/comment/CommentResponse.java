package com.myown.board.dto.comment;

import com.myown.board.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public class CommentResponse {
    private String content;
    private String writer;
}
