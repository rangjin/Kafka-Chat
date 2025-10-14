package com.rangjin.chatapi.infrastructure.auth

import com.rangjin.chatapi.application.user.port.out.PasswordHasher
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class BCryptPasswordHasher : PasswordHasher {

    private val enc = BCryptPasswordEncoder()

    override fun hash(raw: String): String = enc.encode(raw)

    override fun matches(raw: String, hashed: String): Boolean = enc.matches(raw, hashed)

}