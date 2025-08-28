package com.rangjin.chatapi.adapter.`in`.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthFilter (

    private val requestAuthenticator: RequestAuthenticator

): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val context = SecurityContextHolder.getContext()
        val auth = requestAuthenticator.authenticateFromHeader(request.getHeader("Authorization"))
        if (auth != null) context.authentication = auth

        filterChain.doFilter(request, response)
    }

}