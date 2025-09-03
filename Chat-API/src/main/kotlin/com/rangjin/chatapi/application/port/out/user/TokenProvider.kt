package com.rangjin.chatapi.application.port.out.user

interface TokenProvider {

    fun generateToken(userId: Long, username: String): String

}