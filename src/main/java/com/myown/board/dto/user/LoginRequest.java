package com.myown.board.dto.user;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String loginId;
    private String password;
}
