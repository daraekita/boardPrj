package com.myown.board.dto.board;


import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class BoardResponse {

    private Long boardId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;

    public BoardResponse(Long boardId, String title, String content, String author, LocalDateTime createdAt) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }
}
