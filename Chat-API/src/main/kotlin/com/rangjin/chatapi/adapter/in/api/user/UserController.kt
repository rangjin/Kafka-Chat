package com.rangjin.chatapi.adapter.`in`.api.user

import com.rangjin.chatapi.adapter.`in`.api.user.dto.mapper.UserDtoMapper
import com.rangjin.chatapi.adapter.`in`.api.user.dto.request.SignInRequest
import com.rangjin.chatapi.adapter.`in`.api.user.dto.request.SignUpRequest
import com.rangjin.chatapi.adapter.`in`.api.user.dto.response.SignUpResponse
import com.rangjin.chatapi.port.`in`.user.usecase.SignInUseCase
import com.rangjin.chatapi.port.`in`.user.usecase.SignUpUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController (

    private val userDtoMapper: UserDtoMapper,

    private val signUpUseCase: SignUpUseCase,

    private val signInUseCase: SignInUseCase

) {

    @PostMapping("/signUp")
    fun postSignUp(
        @RequestBody request: SignUpRequest
    ): ResponseEntity<SignUpResponse> {
        val user = signUpUseCase.signUp(
            userDtoMapper.toSignUpCommand(request)
        )

        return ResponseEntity.ok().body(
            userDtoMapper.toSignUpResponse(user)
        )
    }

    @PostMapping("/signIn")
    fun postSignIn(
        @RequestBody request: SignInRequest
    ): ResponseEntity<String> {
        return ResponseEntity.ok().body(
            signInUseCase.signIn(userDtoMapper.toSignInCommand(request))
        )
    }

}