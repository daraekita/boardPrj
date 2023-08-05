package com.myown.board.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Board {
    @Id
    private final Long userId;

    private final String title;
    private final String author;
    private final String content;
    private final LocalDateTime createdAt;

    public Board() {
        this.userId = null;
        this.title = null;
        this.author = null;
        this.content = null;
        this.createdAt = null;
    }

    private Board(Long userId, String title, String author, String content, LocalDateTime createdAt) {
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private Long userId;
        private String title;
        private String author;
        private String content;
        private LocalDateTime createdAt;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Board build() {
            return new Board(userId, title, author, content, createdAt);
        }
    }
}
