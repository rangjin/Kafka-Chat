package com.rangjin.chatapi.domain.user.port.out.auth

interface PasswordHasher {

    fun hash(raw: String): String

    fun matches(raw: String, hashed: String): Boolean

}