package com.myown.board.controller;

import com.myown.board.dto.board.AddCommentRequest;
import com.myown.board.dto.board.BoardResponse;
import com.myown.board.dto.board.CreateRequest;
import com.myown.board.dto.board.GetListResponse;
import com.myown.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //단건 게시물 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getDetail(@PathVariable Long boardId){
        BoardResponse boardResponse = boardService.getDetail(boardId);
        return new ResponseEntity(boardResponse, HttpStatus.OK);
    }

    // 게시물 목록조회
    @GetMapping("/list")
    public ResponseEntity<List<GetListResponse>> getBoardList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        List<GetListResponse> boardList = boardService.getBoardList(page, size);
                return new ResponseEntity<>(boardList,HttpStatus.OK);
    }

    // 댓글 달기
    @PostMapping("/addComment")
    public ResponseEntity addComment(@RequestBody AddCommentRequest addCommentRequest){
            boardService.addComment(addCommentRequest);
        return new ResponseEntity(HttpStatus.OK);
    }
}
