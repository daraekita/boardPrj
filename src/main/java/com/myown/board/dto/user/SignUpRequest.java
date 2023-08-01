package com.myown.board.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private int userId;
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String nickname;
}
