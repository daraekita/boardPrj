package com.myown.board.controller;

import com.myown.board.dto.user.LoginRequest;
import com.myown.board.dto.user.PwModifyRequest;
import com.myown.board.dto.user.SignUpRequest;
import com.myown.board.service.UserService;
import lombok.RequiredArgsConstructor;
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
        userService.signUp(signUpRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        userService.login(loginRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 비밀번호 수정
    @PutMapping("/change-password")
    public ResponseEntity changePw(@RequestBody PwModifyRequest pwModifyRequest){
        userService.pwModify(pwModifyRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

}
