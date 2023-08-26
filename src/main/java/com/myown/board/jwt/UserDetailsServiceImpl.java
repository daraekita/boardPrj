package com.myown.board.jwt;

import com.myown.board.common.exception.UserNotFoundException;
import com.myown.board.model.User;
import com.myown.board.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UserNotFoundException {

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException(loginId + "를 찾을 수 없습니다."));
        /**
         * 유저를 찾지 못해 UsernameNotFoundException가 발생하면 JwtAuthenticationEntryPoint를 호출 */

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> user.getRole().getKey()); // key: ROLE_권한

        return new org
                .springframework
                .security
                .core
                .userdetails
                .User(user.getLoginId(), user.getPassword(), authorities);
    }
}