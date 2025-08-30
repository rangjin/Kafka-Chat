package com.rangjin.chatapi.adapter.`in`.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(

    private val authFilter: AuthFilter,

    private val authEntryPoint: AuthEntryPoint

) {

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf {
            it.disable()
        }
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .authorizeHttpRequests {
            it.requestMatchers(
                "/swagger-ui/**", "/v3/api-docs/**",
                "/api/user/signUp", "/api/user/signIn"
            ).permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
        }
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter::class.java)
        .exceptionHandling {
            it.authenticationEntryPoint(authEntryPoint)
        }
        .build()!!

}