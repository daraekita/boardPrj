package com.myown.board.model;

import com.myown.board.dto.board.BoardResponse;
import com.myown.board.dto.board.GetListResponse;
import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private Long userId;
    private String title;
    private String author;
    private String content;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> comment;

    public Board() {}

    private Board(Long userId, String title, String author, String content, LocalDateTime createdAt) {
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    public BoardResponse toDto() {
        return new BoardResponse(this.boardId, this.title, this.author, this.content, this.createdAt);
    }

    public GetListResponse toListDto() {
        return new GetListResponse(this.boardId, this.title, this.author, this.createdAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private Long boardId;
        private Long userId;
        private String title;
        private String author;
        private String content;
        private LocalDateTime createdAt;

        public Builder boardId(Long boardId) {
            this.boardId = boardId;
            return this;
        }

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
