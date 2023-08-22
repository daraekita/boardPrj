package com.myown.board.repository;

import com.myown.board.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Long countByLoginIdAndPassword(String loginId, String password);

    @Modifying
    @Query("UPDATE User u SET u.password = ?2 WHERE u.id = ?1")
    void updatePassword(Long userId, String newPassword);

    Optional<User> findByLoginId(String loginId);
}
