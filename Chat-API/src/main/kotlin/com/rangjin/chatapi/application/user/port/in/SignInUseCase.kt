package com.rangjin.chatapi.application.user.port.`in`

interface SignInUseCase {

    fun signIn(email: String, rawPassword: String): String

}