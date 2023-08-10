package com.myown.board.dto.board;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class AddCommentRequest {
    private Long boardId;
    private String loginId;
    private String content;
}
