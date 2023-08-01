package com.myown.board.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Setter;

@Entity
@Setter
public class User {
    @Id
    private Long userId;

    private String loginId;
    private String password;
    private String name;
    private String email;
}
