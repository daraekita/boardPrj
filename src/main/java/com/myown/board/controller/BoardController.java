package com.myown.board.controller;

import com.myown.board.dto.board.CreateRequest;
import com.myown.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //게시물 생성
    @PostMapping
    public ResponseEntity create(@RequestBody CreateRequest createRequest){
        boardService.post(createRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

}
