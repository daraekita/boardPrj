package com.myown.board.service;

import com.myown.board.common.exception.CustomException;
import com.myown.board.dto.user.LoginRequest;
import com.myown.board.dto.user.PwModifyRequest;
import com.myown.board.dto.user.SignUpRequest;
import com.myown.board.jwt.JwtProvider;
import com.myown.board.jwt.TokenResponse;
import com.myown.board.model.User;
import com.myown.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    // 회원가입
    public void signUp(SignUpRequest signUpRequest) {
        User user = User.builder()
                .loginId(signUpRequest.getLoginId())
                .password(signUpRequest.getPassword())
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail()).build();
        userRepository.save(user);
    }

    public ResponseEntity<TokenResponse> login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getLoginId(),
                            loginRequest.getPassword()
                    )
            );
            TokenResponse tokenResponse = jwtProvider.generateTokenDto(authentication);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + tokenResponse.getAccessToken());

            return new ResponseEntity<>(tokenResponse, httpHeaders, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid credentials supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
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
        userRepository.updatePassword(pwModifyRequest.getUserId(), pwModifyRequest.getPassword1());
    }
}
