package com.rangjin.chatapi.application.port.out.user

interface PasswordHasher {

    fun hash(raw: String): String

    fun matches(raw: String, hashed: String): Boolean

}