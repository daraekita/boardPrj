package com.myown.board.dto.user;

import com.myown.board.jwt.TokenResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginResponse {
    private String AccessToken;
    private String RefreshToken;

    public LoginResponse(TokenResponse tokenResponse) {
        AccessToken = tokenResponse.getAccessToken();
        RefreshToken = tokenResponse.getRefreshToken();
    }
}
