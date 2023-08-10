package com.myown.board.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    private String loginId;
    private String content;
    private LocalDateTime createdAt;
    @Builder
    public Comment(Board board, String loginId, String content, LocalDateTime createdAt) {
        this.board = board;
        this.loginId = loginId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
