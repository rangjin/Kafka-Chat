package com.rangjin.chatapi.common.error

import org.springframework.http.HttpStatus

enum class ErrorCode(

    val status: HttpStatus,

    val message: String

) {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다")
    ;
}