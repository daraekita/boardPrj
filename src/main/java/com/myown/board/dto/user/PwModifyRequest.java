package com.myown.board.dto.user;

import lombok.Getter;

@Getter
public class PwModifyRequest {
    private Long userId;
    private String password1;
    private String password2;

}
