package com.myown.board.service;

import com.myown.board.dto.board.*;
import com.myown.board.model.Board;
import com.myown.board.model.Comment;
import com.myown.board.repository.BoardRepository;
import com.myown.board.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, CommentRepository commentRepository) {
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    public void post(CreateRequest createRequest) {
        Board board = Board.builder()
                .userId(createRequest.getUserId())
                .title(createRequest.getTitle())
                .author(createRequest.getAuthor())
                .content(createRequest.getContent())
                .createdAt(LocalDateTime.now()).build();

        boardRepository.save(board);
    }

    public BoardResponse getDetail(Long boardId) {
        BoardResponse boardResponse = boardRepository.findByBoardId(boardId);
        return boardResponse;
    }

    public List<GetListResponse> getBoardList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.getContent().stream()
                .map(Board::toListDto)
                .collect(Collectors.toList());
    }

    public void addComment(AddCommentRequest addCommentRequest) {
        Optional<Board> boardOptional = boardRepository.findById(addCommentRequest.getBoardId());

        if(boardOptional.isPresent()){
            Comment comment = Comment.builder()
                    .board(boardOptional.get())
                    .loginId(addCommentRequest.getLoginId())
                    .content(addCommentRequest.getContent())
                    .createdAt(LocalDateTime.now())
                    .build();
            commentRepository.save(comment);
        }else {
            throw new IllegalStateException("게시글이 존재하지 않습니다");
        }
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    public void deleteComment(Long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            commentRepository.delete(comment);
        } else {
             throw new IllegalStateException("댓글이 존재하지 않습니다.");
        }
    }
}
