package com.rangjin.chatapi.application.user.port.`in`

import com.rangjin.chatapi.application.user.dto.AuthPrincipal

interface AuthUseCase {

    fun getPrincipal(token: String): AuthPrincipal?

}