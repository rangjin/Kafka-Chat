package com.rangjin.chatapi.port.`in`.user.usecase

import com.rangjin.chatapi.domain.user.model.User
import com.rangjin.chatapi.port.`in`.user.command.SignUpCommand

interface SignUpUseCase {

    fun signUp(command: SignUpCommand): User

}