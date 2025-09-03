package com.rangjin.chatapi.presentation.api.user.controller

import com.rangjin.chatapi.application.port.`in`.user.SignInUseCase
import com.rangjin.chatapi.application.port.`in`.user.SignUpUseCase
import com.rangjin.chatapi.presentation.api.user.dto.request.SignInRequest
import com.rangjin.chatapi.presentation.api.user.dto.request.SignUpRequest
import com.rangjin.chatapi.presentation.api.user.dto.response.TokenResponse
import com.rangjin.chatapi.presentation.api.user.dto.response.UserSummaryResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(

    private val signUpUseCase: SignUpUseCase,

    private val signInUseCase: SignInUseCase

) {

    @PostMapping("/signUp")
    fun postSignUp(
        @RequestBody req: SignUpRequest
    ): UserSummaryResponse {
        val user = signUpUseCase.signUp(req.email, req.username, req.password)

        return UserSummaryResponse.from(user)
    }

    @PostMapping("/signIn")
    fun postSignIn(
        @RequestBody req: SignInRequest
    ): TokenResponse {
        val token = signInUseCase.signIn(req.email, req.password)

        return TokenResponse(token)
    }

}