package com.myown.board.dto.board;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetListResponse {

    private Long boardId;
    private String title;
    private String author;
    private LocalDateTime createdAt;

    public GetListResponse(Long boardId, String title, String author, LocalDateTime createdAt) {
        this.boardId = boardId;
        this.title = title;
        this.author = author;
        this.createdAt = createdAt;
    }
}
