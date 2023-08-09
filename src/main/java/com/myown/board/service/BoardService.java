package com.myown.board.service;

import com.myown.board.dto.board.BoardProjection;
import com.myown.board.dto.board.BoardResponse;
import com.myown.board.dto.board.CreateRequest;
import com.myown.board.model.Board;
import com.myown.board.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 새 글쓰기
    public void post(CreateRequest createRequest) {
        Board board =   Board.builder()
                        .userId(createRequest.getUserId())
                        .title(createRequest.getTitle())
                        .author(createRequest.getAuthor())
                        .content(createRequest.getContent())
                        .createdAt(LocalDateTime.now()).build();

        boardRepository.save(board);
    }

    public BoardResponse getDetail(Long boardId) {
        BoardResponse boardResponse = boardRepository.findByBoardId(boardId);
        log.info("board title = {}",boardResponse.getTitle());
        return boardResponse;
    }
}
