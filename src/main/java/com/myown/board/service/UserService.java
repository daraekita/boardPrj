package com.myown.board.service;

import com.myown.board.common.code.ErrorCode;
import com.myown.board.common.exception.CustomIllegalStateException;
import com.myown.board.dto.user.LoginRequest;
import com.myown.board.dto.user.LoginResponse;
import com.myown.board.dto.user.PwModifyRequest;
import com.myown.board.dto.user.SignUpRequest;
import com.myown.board.jwt.JwtProvider;
import com.myown.board.jwt.TokenResponse;
import com.myown.board.model.User;
import com.myown.board.repository.UserRepository;
import com.myown.board.util.RedisUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder encoder;
    private final RedisUtil redisUtil;

    @Autowired
    public UserService(UserRepository userRepository, JwtProvider jwtProvider, AuthenticationManagerBuilder authenticationManagerBuilder, BCryptPasswordEncoder encoder, RedisUtil redisUtil) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.encoder = encoder;
        this.redisUtil = redisUtil;
    }

    // 회원가입
    public ResponseEntity signUp(SignUpRequest signUpRequest) {
        if(userRepository.existsByLoginId(signUpRequest.getLoginId())){
            throw new CustomIllegalStateException(ErrorCode.LOGINID_CONFLICT);
        }
        User user = User.builder()
                .loginId(signUpRequest.getLoginId())
                .password(signUpRequest.getPassword())
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail()).build();
        user.encodingPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 완료");
    }

    // 로그인
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

    public TokenResponse renewToken(String accessToken, String refreshToken) {
        // 1. 검증
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new CustomIllegalStateException(ErrorCode.INVALID_JWT);
        }

        // 2. Access Token 에서 User ID 가져오기
        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        String userId = authentication.getName();

        // 3. 저장소에서 User ID 를 기반으로 Refresh Token 값 가져오기
        String findRefreshToken = redisUtil.getData(userId);
        if (findRefreshToken == null) {
            throw new CustomIllegalStateException(ErrorCode.INVALID_JWT);
        }

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.equals(findRefreshToken)) {
            throw new CustomIllegalStateException(ErrorCode.NO_MATCHES_INFO);
        }

        // 5. 새로운 토큰 생성
        TokenResponse tokenResponse = jwtProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        redisUtil.setDataExpire(userId, tokenResponse.getRefreshToken(), 1000 * 60 * 60 * 24 * 7);

        return tokenResponse;
    }
}
