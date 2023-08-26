package com.myown.board.dto.user;

import com.myown.board.jwt.TokenResponse;
import lombok.Getter;

@Getter
public class LoginResponse {
    private String AccessToken;
    private String RefreshToken;
    private Long AccessTokenExpireTime;

    public LoginResponse(TokenResponse tokenResponse) {
        AccessToken = tokenResponse.getAccessToken();
        RefreshToken = tokenResponse.getRefreshToken();
        AccessTokenExpireTime = tokenResponse.getAccessTokenExpiresIn();
    }
}
