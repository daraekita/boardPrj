package com.myown.board.model;

import com.myown.board.dto.board.BoardResponse;
import com.myown.board.dto.board.GetListResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
