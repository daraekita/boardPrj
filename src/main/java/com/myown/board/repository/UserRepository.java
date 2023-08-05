package com.myown.board.repository;

import com.myown.board.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    Long countByLoginIdAndPassword(String loginId, String password);
}
