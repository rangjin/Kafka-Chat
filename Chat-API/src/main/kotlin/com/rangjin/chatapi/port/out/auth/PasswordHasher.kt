package com.rangjin.chatapi.port.out.auth

interface PasswordHasher {

    fun hash(raw: String): String

    fun matches(raw: String, hashed: String): Boolean

}