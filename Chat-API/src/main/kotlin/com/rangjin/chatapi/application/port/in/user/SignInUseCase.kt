package com.rangjin.chatapi.application.port.`in`.user

interface SignInUseCase {

    fun signIn(email: String, rawPassword: String): String

}