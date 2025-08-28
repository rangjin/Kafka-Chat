package com.rangjin.chatapi.adapter.`in`.api.user.controller

import com.rangjin.chatapi.adapter.`in`.api.user.mapper.UserCommandMapper
import com.rangjin.chatapi.adapter.`in`.api.user.dto.request.SignInRequest
import com.rangjin.chatapi.adapter.`in`.api.user.dto.request.SignUpRequest
import com.rangjin.chatapi.adapter.`in`.api.user.dto.response.SignInResponse
import com.rangjin.chatapi.adapter.`in`.api.user.dto.response.SignUpResponse
import com.rangjin.chatapi.port.`in`.user.usecase.SignInUseCase
import com.rangjin.chatapi.port.`in`.user.usecase.SignUpUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController (

    private val userCommandMapper: UserCommandMapper,

    private val signUpUseCase: SignUpUseCase,

    private val signInUseCase: SignInUseCase

) {

    @PostMapping("/signUp")
    fun postSignUp(
        @RequestBody request: SignUpRequest
    ): SignUpResponse {
        val user = signUpUseCase.signUp(userCommandMapper.toSignUpCommand(request))

        return userCommandMapper.toSignUpResponse(user)
    }

    @PostMapping("/signIn")
    fun postSignIn(
        @RequestBody request: SignInRequest
    ): SignInResponse {
        val token = signInUseCase.signIn(userCommandMapper.toSignInCommand(request))

        return userCommandMapper.toSignInResponse(token)
    }

}