package com.rangjin.chatapi.application.user.port.out

interface PasswordHasher {

    fun hash(raw: String): String

    fun matches(raw: String, hashed: String): Boolean

}