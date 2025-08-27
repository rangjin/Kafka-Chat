package com.rangjin.chatapi.adapter.out.security.bcrypt

import com.rangjin.chatapi.port.out.security.PasswordHasher
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class BCryptPasswordHasher: PasswordHasher {

    private val enc = BCryptPasswordEncoder()

    override fun hash(raw: String): String = enc.encode(raw)

    override fun matches(raw: String, hashed: String): Boolean = enc.matches(raw, hashed)

}