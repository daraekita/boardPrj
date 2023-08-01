package com.myown.board.dto.user;


import lombok.Getter;


@Getter
public class SignUpRequest {
    private Long userId;
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String nickname;
}
