package com.rangjin.chatapi.domain.user.port.out.auth

interface TokenProvider {

    fun generateToken(userId: Long, username: String): String

}