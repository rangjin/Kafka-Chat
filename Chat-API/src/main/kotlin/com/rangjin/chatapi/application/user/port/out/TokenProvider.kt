package com.rangjin.chatapi.application.user.port.out

import com.rangjin.chatapi.application.user.dto.AuthPrincipal

interface TokenProvider {

    fun generateToken(userId: Long, username: String): String

    fun authenticateFromHeader(header: String?): AuthPrincipal?

}