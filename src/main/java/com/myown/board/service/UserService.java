package com.myown.board.service;

import com.myown.board.dto.user.SignUpRequest;
import com.myown.board.model.User;
import com.myown.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public void signUp(SignUpRequest signUpRequest) {
        User user = new User();

        user.setLoginId(signUpRequest.getLoginId());
        user.setPassword(signUpRequest.getPassword());
        user.setName(signUpRequest.getNickname());
        user.setEmail(signUpRequest.getEmail());

        userRepository.save(user);

    }
}
