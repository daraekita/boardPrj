package com.myown.board.common.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Can't find User");
    }

}
