package com.rangjin.chatapi.port.out.security

interface PasswordHasher {

    fun hash(raw: String): String

    fun matches(raw: String, hashed: String): Boolean

}