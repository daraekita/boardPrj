package com.myown.board.dto.board;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class CreateRequest {
    private  Long userId;
    private  String title;
    private  String author;
    private  String content;
    private  LocalDateTime createdAt;

}
