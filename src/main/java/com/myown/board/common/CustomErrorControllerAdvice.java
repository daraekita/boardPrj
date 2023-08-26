package com.myown.board.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class CustomErrorControllerAdvice {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        // 예외 처리 로직 및 응답 생성
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Custom error message");
    }
}