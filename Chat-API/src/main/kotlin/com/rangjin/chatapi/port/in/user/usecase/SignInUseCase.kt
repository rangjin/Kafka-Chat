package com.rangjin.chatapi.port.`in`.user.usecase

import com.rangjin.chatapi.port.`in`.user.command.SignInCommand

interface SignInUseCase {

    fun signIn(signInCommand: SignInCommand): String

}