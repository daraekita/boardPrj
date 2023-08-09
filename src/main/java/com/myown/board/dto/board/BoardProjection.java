package com.myown.board.dto.board;

import java.time.LocalDateTime;

public interface BoardProjection {
    Long boardId();
     String title();
     String content();
     String author();
     LocalDateTime createdAt();
}
