package com.rangjin.chatapi.domain.user.port.`in`.usecase

import com.rangjin.chatapi.domain.user.port.`in`.command.SignInCommand

interface SignInUseCase {

    fun signIn(signInCommand: SignInCommand): String

}