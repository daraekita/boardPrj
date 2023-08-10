package com.myown.board.model;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
@Entity
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

}
