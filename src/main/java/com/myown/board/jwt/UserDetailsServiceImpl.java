package com.myown.board.jwt;

import com.myown.board.common.exception.UserNotFoundException;
import com.myown.board.model.User;
import com.myown.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UserNotFoundException {
        System.out.println("loingId in loadUserByUsername = " + loginId);
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(UserNotFoundException::new);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        return new org
                .springframework
                .security
                .core
                .userdetails
                .User(user.getLoginId(), user.getPassword(), grantedAuthorities);
    }
}