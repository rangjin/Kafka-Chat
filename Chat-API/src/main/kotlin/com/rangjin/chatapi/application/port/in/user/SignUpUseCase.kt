package com.rangjin.chatapi.application.port.`in`.user

import com.rangjin.chatapi.domain.user.User

interface SignUpUseCase {

    fun signUp(email: String, username: String, password: String): User

}