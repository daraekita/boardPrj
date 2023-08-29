package com.myown.board.repository;

import com.myown.board.dto.board.BoardResponse;
import com.myown.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT new com.myown.board.dto.board.BoardResponse(b.boardId, b.title, b.content, b.author, b.createdAt) FROM Board b WHERE b.boardId = :boardId")
    Optional<BoardResponse> findByBoardId(@Param("boardId") Long boardId);

    @Query("SELECT b FROM Board b WHERE b.boardId = :boardId")
    Optional<Board> findByBoardIdCustom(@Param("boardId") Long boardId);

    Optional<Board> findByUserId(Long userId);
}
