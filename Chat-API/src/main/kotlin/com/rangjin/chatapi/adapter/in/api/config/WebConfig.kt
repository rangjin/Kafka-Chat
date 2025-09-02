package com.rangjin.chatapi.adapter.`in`.api.config

import com.rangjin.chatapi.adapter.`in`.api.interceptor.FieldsInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(

    private val fieldsInterceptor: FieldsInterceptor

) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(fieldsInterceptor).addPathPatterns("/**")
    }

}