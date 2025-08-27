package com.rangjin.chatapi.adapter.out.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter (

    private val jwtTokenProvider: JwtTokenProvider

): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Authorization")

        if (token != null) {
            val split = token.split(" ")
            if (split.size == 2 && split[0] == "Bearer" && jwtTokenProvider.verifyToken(split[1])) {
                val auth = jwtTokenProvider.getAuthentication(split[1])
                SecurityContextHolder.getContext().authentication = auth
            }
        }

        filterChain.doFilter(request, response)
    }

}