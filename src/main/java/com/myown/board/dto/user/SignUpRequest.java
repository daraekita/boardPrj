package com.myown.board.dto.user;


import lombok.Getter;


@Getter
public class SignUpRequest {
    private String loginId;
    private String password;
    private String name;
    private String email;
}
