package com.myown.board.common.code;

import lombok.Getter;

@Getter
public enum ErrorCode {
    /**
     * HTTP Status Code
     * 400 : Bad Request
     * 401 : Unauthorized
     * 403 : Forbidden
     * 404 : Not Found
     * 500 : Internal Server Error
     * 503 : Service Unavailable
     */
    //user
    REDIS_ERROR(503, "U001", "Reids연결이 실패했습니다"),
    INVALID_JWT(401,"U002","유효하지 않은 JWT 입니다.");

    //에러의 코드 상태 반환
    private final int status;

    //에러 코드의 코드 간의 구분 값 ex) user 첫번째 > U001
    private final String divisionCode;

    // 에러 코드의 메시지
    private final String message;

    ErrorCode(int status, String divisionCode, String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
