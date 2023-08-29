package com.myown.board.service;

import com.myown.board.dto.board.*;
import com.myown.board.dto.comment.AddCommentRequest;
import com.myown.board.dto.comment.CommentResponse;
import com.myown.board.model.Board;
import com.myown.board.model.Comment;
import com.myown.board.model.User;
import com.myown.board.repository.BoardRepository;
import com.myown.board.repository.CommentRepository;
import com.myown.board.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final UserRepository userRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public void post(CreateRequest createRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String loginId = authentication.getName();

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(()->new UsernameNotFoundException(loginId + "를 찾을 수 없습니다"));
        Long userId = user.getUserId();
        String author = user.getLoginId();

        Board board = Board.builder()
                .userId(userId)
                .title(createRequest.getTitle())
                .author(author)
                .content(createRequest.getContent())
                .createdAt(LocalDateTime.now()).build();

        boardRepository.save(board);
    }

    public BoardResponse getDetail(Long boardId) {
        BoardResponse boardResponse = boardRepository.findByBoardId(boardId)
                .orElseThrow(()->  new IllegalStateException("찾는 게시물이 없습니다."));
        return boardResponse;
    }

    public List<GetListResponse> getBoardList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.getContent().stream()
                .map(Board::toListDto)
                .collect(Collectors.toList());
    }

    public void addComment(Long boardId, AddCommentRequest addCommentRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(()->new UsernameNotFoundException(loginId + "를 찾을 수 없습니다"));

        Board board = boardRepository.findByBoardIdCustom(boardId)
                .orElseThrow(()->new UsernameNotFoundException("찾는 게시판이 없습니다."));

        Comment comment = Comment.builder()
                        .loginId(loginId)
                        .content(addCommentRequest.getContent())
                        .user(user)
                        .board(board)
                        .createdAt(LocalDateTime.now()).build();

            commentRepository.save(comment);
        }



    public void deleteBoard(Long boardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        BoardResponse boardResponse = boardRepository.findByBoardId(boardId).orElseThrow(()->new UsernameNotFoundException("게시물 없음"));

        if(!loginId.equals(boardResponse.getLoginId())){
            throw new IllegalStateException("삭제할 권한이 없습니다");
        }
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
