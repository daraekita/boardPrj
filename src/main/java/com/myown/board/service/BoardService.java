package com.myown.board.service;

import com.myown.board.dto.board.PostRequest;
import com.myown.board.model.Board;
import com.myown.board.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 새 글쓰기
    public void post(PostRequest postRequest) {
        Board board = Board.builder()
                        .userId(postRequest.getUserId())
                        .title(postRequest.getTitle())
                        .author(postRequest.getAuthor())
                        .content(postRequest.getContent())
                        .createdAt(postRequest.getCreatedAt()).build();

        boardRepository.save(board);
    }
}
