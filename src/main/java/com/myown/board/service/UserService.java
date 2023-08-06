package com.myown.board.service;

import com.myown.board.dto.user.LoginRequest;
import com.myown.board.dto.user.SignUpRequest;
import com.myown.board.model.User;
import com.myown.board.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public void signUp(SignUpRequest signUpRequest) {
        User user = User.builder()
                .loginId(signUpRequest.getLoginId())
                .password(signUpRequest.getPassword())
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail()).build();

//        user.setLoginId(signUpRequest.getLoginId());
//        user.setPassword(signUpRequest.getPassword());
//        user.setName(signUpRequest.getName());
//        user.setEmail(signUpRequest.getEmail());
        userRepository.save(user);
    }

    public void login(LoginRequest loginRequest) {
        String loginId = loginRequest.getLoginId();
        String password = loginRequest.getPassword();
        Long cnt = userRepository.countByLoginIdAndPassword(loginId, password);
        log.info("cnt: {}", cnt);

        if(cnt<1){
            throw new IllegalStateException("일치하는 회원 정보가 없습니다");
        }
    }
}
