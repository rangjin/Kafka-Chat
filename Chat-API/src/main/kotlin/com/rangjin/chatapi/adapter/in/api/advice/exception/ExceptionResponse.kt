package com.rangjin.chatapi.adapter.`in`.api.advice.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ExceptionResponse<T>(

    val time: LocalDateTime = LocalDateTime.now(),

    val status: HttpStatus,

    val requestUri: String,

    val data: T

) {
}