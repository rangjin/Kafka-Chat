package com.rangjin.chatapi.port.out.security

interface TokenProvider {

    fun generateToken(userId: Long, username: String): String

    fun verifyToken(token: String): Boolean

}