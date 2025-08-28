package com.rangjin.chatapi.port.out.auth

interface TokenProvider {

    fun generateToken(userId: Long, username: String): String

}