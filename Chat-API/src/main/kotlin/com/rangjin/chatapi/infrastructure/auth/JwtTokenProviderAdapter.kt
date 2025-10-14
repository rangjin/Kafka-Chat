package com.rangjin.chatapi.infrastructure.auth

import com.rangjin.chatapi.application.user.port.out.TokenProvider
import com.rangjin.chatapi.application.user.dto.AuthPrincipal
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProviderAdapter(

    @Value("\${security.jwt.secret}")
    private val secret: String,

    @Value("\${security.jwt.expiration}")
    private val expiration: Long

) : TokenProvider {

    private final val key = Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8))

    private final val parser = Jwts.parser().verifyWith(key).build()

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

    override fun authenticateFromHeader(header: String?): AuthPrincipal? {
        val token = extractBearer(header) ?: return null
        return parseAndValidate(token)

//        return UsernamePasswordAuthenticationToken(
//            principal, null, emptyList()
//        )
    }

    private fun extractBearer(header: String?): String? =
        header?.trim()
            ?.takeIf { it.startsWith("Bearer ", ignoreCase = true) }
            ?.substring(7)
            ?.takeIf { it.isNotBlank() }

    private fun parseAndValidate(token: String) = try {
        val claims = parser.parseSignedClaims(token).payload

        // todo: id와 username 확인 절차 필요?
        AuthPrincipal(
            userId = claims.subject.toLong(),
            username = claims["username"] as String?
        )
    } catch (_: Exception) {
        null
    }

}