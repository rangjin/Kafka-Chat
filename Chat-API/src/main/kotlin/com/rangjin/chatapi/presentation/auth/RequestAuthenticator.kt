package com.rangjin.chatapi.presentation.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class RequestAuthenticator(

    @Value("\${security.jwt.secret}")
    private val secret: String

) {

    private val key = Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8))

    private val parser = Jwts.parser().verifyWith(key).build()

    fun authenticateFromHeader(header: String?): Authentication? {
        val token = extractBearer(header) ?: return null
        val principal = parseAndValidate(token) ?: return null

        return UsernamePasswordAuthenticationToken(
            principal, null, emptyList()
        )
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