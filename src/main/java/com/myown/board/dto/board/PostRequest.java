package com.myown.board.dto.board;

import java.time.LocalDateTime;


public class PostRequest {
    private final Long userId;
    private final String title;
    private final String author;
    private final String content;
    private final LocalDateTime createdAt;

    public PostRequest() {
        this.userId = null;
        this.title = null;
        this.author = null;
        this.content = null;
        this.createdAt = null;
    }

    public PostRequest(Long userId, String title, String author, String content, LocalDateTime createdAt) {
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
