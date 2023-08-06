package com.myown.board.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String loginId;
    private String password;
    private String name;
    private String email;

    private User(String loginId, String password, String name, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String loginId;
        private String password;
        private String name;
        private String email;

        public Builder loginId(String loginId) {
            this.loginId = loginId;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public User build(){
            return new User(loginId, password, name, email);
        }
    }
}
