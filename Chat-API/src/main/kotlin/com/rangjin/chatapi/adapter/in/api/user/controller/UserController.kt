package com.rangjin.chatapi.adapter.`in`.api.user.controller

import com.rangjin.chatapi.adapter.`in`.api.user.dto.request.SignInRequest
import com.rangjin.chatapi.adapter.`in`.api.user.dto.request.SignUpRequest
import com.rangjin.chatapi.adapter.`in`.api.user.dto.response.TokenResponse
import com.rangjin.chatapi.adapter.`in`.api.user.dto.response.UserWithoutPasswordResponse
import com.rangjin.chatapi.adapter.`in`.api.user.mapper.toSignInCommand
import com.rangjin.chatapi.adapter.`in`.api.user.mapper.toSignUpCommand
import com.rangjin.chatapi.adapter.`in`.api.user.mapper.toUserWithoutPasswordResponse
import com.rangjin.chatapi.domain.user.port.`in`.usecase.SignInUseCase
import com.rangjin.chatapi.domain.user.port.`in`.usecase.SignUpUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(

    private val signUpUseCase: SignUpUseCase,

    private val signInUseCase: SignInUseCase

) {

    @PostMapping("/signUp")
    fun postSignUp(
        @RequestBody request: SignUpRequest
    ): UserWithoutPasswordResponse {
        val user = signUpUseCase.signUp(request.toSignUpCommand())

        return user.toUserWithoutPasswordResponse()
    }

    @PostMapping("/signIn")
    fun postSignIn(
        @RequestBody request: SignInRequest
    ): TokenResponse {
        val token = signInUseCase.signIn(request.toSignInCommand())

        return TokenResponse(token)
    }

}