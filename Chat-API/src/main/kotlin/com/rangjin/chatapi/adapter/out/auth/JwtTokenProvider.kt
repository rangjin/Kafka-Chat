package com.rangjin.chatapi.adapter.out.auth

import com.rangjin.chatapi.port.out.auth.TokenProvider
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(

    @Value("\${security.jwt.secret}")
    private val secret: String,

    @Value("\${security.jwt.expiration}")
    private val expiration: Long

) : TokenProvider {

    override fun generateToken(userId: Long, username: String): String {
        val now = Date()
        return Jwts.builder()
            .claims(
                Jwts.claims()
                    .subject(userId.toString())
                    .add(mapOf(Pair("username", username)))
                    .build()
            )
            .issuedAt(now)
            .expiration(Date(now.time + expiration))
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8)))
            .compact()
    }

}