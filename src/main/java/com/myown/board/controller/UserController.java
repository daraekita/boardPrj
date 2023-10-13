package com.myown.board.controller;

import com.myown.board.dto.user.LoginRequest;
import com.myown.board.dto.user.LoginResponse;
import com.myown.board.dto.user.PwModifyRequest;
import com.myown.board.dto.user.SignUpRequest;
import com.myown.board.jwt.TokenResponse;
import com.myown.board.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody SignUpRequest signUpRequest){
        return userService.signUp(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = userService.login(loginRequest);
        return new ResponseEntity(loginResponse,HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity changePw(@RequestBody PwModifyRequest pwModifyRequest){
        userService.pwModify(pwModifyRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity renewToken(@RequestHeader(value = "Authorization") String acTokenRequest,
                                  @RequestHeader(value = "RefreshToken") String rfTokenRequest){
        String accessToken = acTokenRequest.substring(7);
        String refreshToken = rfTokenRequest.substring(7);

        TokenResponse tokenResponse = userService.renewToken(accessToken, refreshToken);

        return new ResponseEntity(tokenResponse, HttpStatus.OK);
    }
}
