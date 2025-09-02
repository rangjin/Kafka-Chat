package com.rangjin.chatapi.adapter.`in`.api.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class FieldsInterceptor: HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val raw = request.getParameter("fields") ?: return true
        val fields = raw.split(',')
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .toSet()
        request.setAttribute("FIELDS_PARAM", fields)

        return true
    }

}