package com.rangjin.chatapi.adapter.out.security.jwt

import com.rangjin.chatapi.adapter.out.persistence.user.repository.UserJpaRepository
import com.rangjin.chatapi.port.out.security.TokenProvider
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JwtTokenProvider (

    @Value("\${security.jwt.secret}")
    private val secret: String,

    @Value("\${security.jwt.expiration}")
    private val expiration: Long,

    private val userJpaRepository: UserJpaRepository

): TokenProvider {

    override fun generateToken(userId: Long, username: String): String {
        val now = Date()
        return Jwts.builder()
            .claims(Jwts.claims()
                .subject(userId.toString())
                .add(mapOf(Pair("username", username)))
                .build())
            .issuedAt(now)
            .expiration(Date(now.time + expiration))
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8)))
            .compact()
    }

    override fun verifyToken(token: String): Boolean {
        return try {
            val userEntity = userJpaRepository.findByIdOrNull(getId(token).toLong())
                ?: throw Exception()
            val claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
            if (claims.payload["username"] != userEntity.username) {
                return false
            }

            return claims.payload
                .expiration
                .after(Date())
        } catch (_: Exception) {
            false
        }
    }

    fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val userEntity = userJpaRepository.findByIdOrNull(getId(token).toLong())

        return UsernamePasswordAuthenticationToken(
            userEntity,
            "",
            emptyList()
        )
    }

    private fun getId(token: String): String {
        return try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .payload
                .subject
        } catch (_: Exception) {
            throw Exception()
        }
    }

}