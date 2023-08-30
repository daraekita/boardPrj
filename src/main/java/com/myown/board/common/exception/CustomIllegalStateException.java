package com.myown.board.common.exception;

import com.myown.board.common.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomIllegalStateException extends IllegalStateException{

    private final ErrorCode errorCode;

    public CustomIllegalStateException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

