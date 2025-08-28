package com.rangjin.chatapi.domain.user.port.`in`.usecase

import com.rangjin.chatapi.domain.user.model.User
import com.rangjin.chatapi.domain.user.port.`in`.command.SignUpCommand

interface SignUpUseCase {

    fun signUp(command: SignUpCommand): User

}