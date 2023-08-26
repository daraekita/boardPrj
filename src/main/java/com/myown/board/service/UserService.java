package com.myown.board.service;

import com.myown.board.dto.user.LoginRequest;
import com.myown.board.dto.user.LoginResponse;
import com.myown.board.dto.user.PwModifyRequest;
import com.myown.board.dto.user.SignUpRequest;
import com.myown.board.jwt.JwtProvider;
import com.myown.board.jwt.TokenResponse;
import com.myown.board.model.User;
import com.myown.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, JwtProvider jwtProvider, AuthenticationManager authenticationManager, AuthenticationManagerBuilder authenticationManagerBuilder, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.encoder = encoder;
    }

    // 회원가입
    public void signUp(SignUpRequest signUpRequest) {
        User user = User.builder()
                .loginId(signUpRequest.getLoginId())
                .password(signUpRequest.getPassword())
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail()).build();
        user.encodingPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());

        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenResponse tokenResponse = jwtProvider.generateTokenDto(authenticate);

        LoginResponse loginResponse = new LoginResponse(tokenResponse);

            return loginResponse;
    }

    public void showEditForm(Long userId) {
        Optional<User> userOptional =  userRepository.findById(userId);

        if(userOptional.isPresent()){
            User user = userOptional.get();
        }
    }

    public void pwModify(PwModifyRequest pwModifyRequest) {
        String password1 = pwModifyRequest.getPassword1();
        String password2 = pwModifyRequest.getPassword2();

        if (password1 == null || password2 == null) {
            throw new IllegalStateException("암호 값이 null 입니다");
        } else if (!password1.equals(password2)) {
            throw new IllegalStateException("두 비밀번호가 일치하지 않습니다");
        }
        String encodedPw = encoder.encode(password1);
        userRepository.updatePassword(pwModifyRequest.getUserId(), encodedPw);

    }
}
