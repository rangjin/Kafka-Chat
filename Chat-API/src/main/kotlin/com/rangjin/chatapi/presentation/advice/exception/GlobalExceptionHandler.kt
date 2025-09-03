package com.rangjin.chatapi.presentation.advice.exception

import com.rangjin.chatapi.common.error.CustomException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    // Custom Exception
    @ExceptionHandler(CustomException::class)
    private fun handlerCustomException(
        e: CustomException,
        request: HttpServletRequest,
    ): ResponseEntity<ExceptionResponse<String>> {
        return ResponseEntity.status(e.errorCode.status).body(
            ExceptionResponse(
                status = e.errorCode.status,
                requestUri = request.requestURI,
                data = e.errorCode.message
            )
        )
    }

    // 보안용
//    @ExceptionHandler(Exception::class)
//    private fun handlerException(
//        e: Exception,
//        request: HttpServletRequest,
//    ): ResponseEntity<ExceptionResponse<String>> {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//            ExceptionResponse(
//                status = HttpStatus.INTERNAL_SERVER_ERROR,
//                requestUri = request.requestURI,
//                data = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
//            )
//        )
//    }

}